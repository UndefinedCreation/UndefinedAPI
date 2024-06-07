package com.redmagic.undefinedapi.nms.v1_20_5.event

import com.redmagic.undefinedapi.customEvents.PlayerArmSwingEvent
import com.redmagic.undefinedapi.customEvents.PlayerArmorChangeEvent
import com.redmagic.undefinedapi.customEvents.PlayerExtinguishEvent
import com.redmagic.undefinedapi.customEvents.PlayerIgniteEvent
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.nms.ClickType
import com.redmagic.undefinedapi.nms.PlayerInteract
import com.redmagic.undefinedapi.nms.extensions.getMetaDataInfo
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import com.redmagic.undefinedapi.nms.extensions.removeMetaData
import com.redmagic.undefinedapi.nms.extensions.setMetaData
import com.redmagic.undefinedapi.nms.v1_20_5.NMSManager
import com.redmagic.undefinedapi.nms.v1_20_5.SpigotNMSMappings
import com.redmagic.undefinedapi.nms.v1_20_5.extensions.*
import com.redmagic.undefinedapi.scheduler.sync
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ServerboundInteractPacket
import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * This class represents a packet listener for Minecraft version 1.20.5.
 * It listens for specific events, such as when a player joins or quits the server,
 * and performs certain actions when those events occur.
 *
 * @constructor Creates an instance of PacketListenerManager1_20_5.
 */
class PacketListenerManager {

    private val armorSlots: HashMap<Int, Int> = hashMapOf(Pair(4, 39), Pair(5, 38), Pair(6, 37), Pair(7, 36))


    init {

        event<PlayerJoinEvent> {

            if (player.fireTicks > 0) {
                player.setMetaData("onFire", true)
            }


            val fakeConnection = player.getConnection().getConnection()

            val channel = fakeConnection.channel
            val pipeline = channel.pipeline()
            pipeline.addBefore("packet_handler", "${player.uniqueId}_UNDEFINEDAPI", UndefinedDuplexHandler(
                {

                    when (this) {
                        is ServerboundSwingPacket -> {
                            val event = PlayerArmSwingEvent(player, if (this.hand == InteractionHand.MAIN_HAND) EquipmentSlot.HAND else EquipmentSlot.OFF_HAND)
                            Bukkit.getPluginManager().callEvent(event)
                            if (event.isCancelled) return@UndefinedDuplexHandler true
                        }
                        is ServerboundInteractPacket -> handleNPCInteract(this)
                    }

                    return@UndefinedDuplexHandler false
                },{

                    sync {
                        when (this@UndefinedDuplexHandler) {
                            is ClientboundContainerSetSlotPacket -> handleArmorChange(player, this@UndefinedDuplexHandler)
                            is ClientboundSetEntityDataPacket -> handleFire(player, this@UndefinedDuplexHandler)
                        }
                    }

                    return@UndefinedDuplexHandler false
                }
            ))

        }

        event<PlayerQuitEvent> {

            player.removeMetaData("onFire")

            val connection = player.getConnection().getConnection()
            val channel = connection.channel
            channel.eventLoop().submit(){
                channel.pipeline().remove("${player.uniqueId}_UNDEFINEDAPI")
            }
        }

    }

    private fun handleArmorChange(player: Player, msg: ClientboundContainerSetSlotPacket) {
        val sPlayer = (player as CraftPlayer).handle
        val windowID = sPlayer.containerMenu.containerId

        val contairID = msg.getPrivateField<Int>(SpigotNMSMappings.ClientboundContainerSetSlotPacketContairID)

        if (windowID != contairID) return

        val slot = msg.getPrivateField<Int>(SpigotNMSMappings.ClientboundContainerSetSlotPacketSlot)

        if (!armorSlots.containsKey(slot)) return

        val bukkitSlot = armorSlots[slot]!!

        val itemStack = msg.getPrivateField<ItemStack>(SpigotNMSMappings.ClientboundContainerSetSlotPacketItemStack)

        sync { Bukkit.getPluginManager().callEvent(PlayerArmorChangeEvent(player, itemStack.bukkitStack, bukkitSlot)) }

    }

    /**
     * Handles the fire event for a player based on a given packet.
     * If the player is on fire, it triggers the PlayerIgniteEvent. If the player's fire is extinguished,
     * it triggers the PlayerExtinguishEvent.
     *
     * @param player The player affected by the fire event.
     * @param msg The packet containing the fire event data.
     */
    private fun handleFire(player: Player, msg: ClientboundSetEntityDataPacket) {

        val id = msg.getEntityID()

        if (player.entityId == id){

            val list = msg.getSynchedEntityDataList()
            

            list.filter { it.id == 0 && it.value is Byte }.forEach {
                if (it.value == 0.toByte()) {
                    player.getMetaDataInfo("onFire")?.also {
                        Bukkit.getPluginManager().callEvent(PlayerExtinguishEvent(player))
                        player.removeMetaData("onFire")
                    }
                } else if (it.value == 1.toByte() && player.getMetaDataInfo("onFire") == null) {
                    Bukkit.getPluginManager().callEvent(PlayerIgniteEvent(player))
                    player.setMetaData("onFire", true)
                }
            }

        }

        return
    }

    /**
     * Handles the interaction between an NPC and a player.
     *
     * @param msg The ServerboundInteractPacket representing the interaction message.
     */
    private fun handleNPCInteract(msg: ServerboundInteractPacket){

        val actionN = msg.getAction()
        val firstChar = actionN.toString().split("$")[1][0]

        if(isRemapped()){
            if (actionN.toString().contains("InteractionAction")) { return }
        }else{
            if (firstChar != 'e' && firstChar != '1') { return }
        }

        val attacking = msg.isAttacking()

        if (!attacking){
            if (!msg.isMainHand()) return
        }

        val action = if(attacking) ClickType.LEFT_CLICK else ClickType.RIGHT_CLICK

        NMSManager.npcInteraction.entries.forEach {
            if (it.key.getEntityID() == msg.getEntityID()) {
                it.value.invoke(PlayerInteract(action, it.key))
            }
        }
    }

}