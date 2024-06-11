package com.redmagic.undefinedapi.nms.v1_20_4.event

import com.redmagic.undefinedapi.customEvents.*
import com.redmagic.undefinedapi.nms.v1_20_4.NMSManager
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.nms.extensions.getMetaDataInfo
import com.redmagic.undefinedapi.nms.extensions.removeMetaData
import com.redmagic.undefinedapi.nms.extensions.setMetaData
import com.redmagic.undefinedapi.nms.ClickType
import com.redmagic.undefinedapi.nms.PlayerInteract
import com.redmagic.undefinedapi.nms.v1_20_4.extensions.*
import com.redmagic.undefinedapi.scheduler.sync
import net.minecraft.network.protocol.game.*
import net.minecraft.world.InteractionHand
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * This class represents a packet listener for Minecraft version 1.20.4.
 * It listens for specific events, such as when a player joins or quits the server,
 * and performs certain actions when those events occur.
 *
 * @constructor Creates an instance of PacketListenerManager1_20_4.
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
                            val event = PlayerArmSwingEvent(player, if (this.hand.equals(InteractionHand.MAIN_HAND)) EquipmentSlot.HAND else EquipmentSlot.OFF_HAND)
                            Bukkit.getPluginManager().callEvent(event)
                            if (event.isCancelled) return@UndefinedDuplexHandler true
                        }
                        is ServerboundInteractPacket -> handleNPCInteract(this)
                        is ServerboundSetCarriedItemPacket -> handleMainHandSwitch(this, player)
                    }

                return@UndefinedDuplexHandler false
            },{

                    when (this@UndefinedDuplexHandler) {
                        is ClientboundSetEntityDataPacket -> handleDataPacket(player, this@UndefinedDuplexHandler)
                        is ClientboundContainerSetSlotPacket -> handleArmorChange(player, this@UndefinedDuplexHandler)
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


    private fun handleMainHandSwitch(msg: ServerboundSetCarriedItemPacket, player: Player) {

        val slot = msg.getEntityID()

        val item = player.inventory.getItem(slot)

        sync { Bukkit.getPluginManager().callEvent(PlayerMainHandSwitchEvent(player, item)) }

    }

    private fun handleArmorChange(player: Player, msg: ClientboundContainerSetSlotPacket) {
        val sPlayer = (player as CraftPlayer).handle
        val windowID = sPlayer.containerMenu.containerId

        val contairID = msg.getContainerID()

        if (windowID != contairID) return

        val slot = msg.getContainerSlot()

        if (!armorSlots.containsKey(slot)) return

        val bukkitSlot = armorSlots[slot]!!

        val itemStack = msg.getItemStack()

        sync { Bukkit.getPluginManager().callEvent(PlayerArmorChangeEvent(player, itemStack.bukkitStack, bukkitSlot)) }

    }

    /**
     * Handles the interaction between a player and fire.
     *
     * @param player The player involved in the interaction.
     * @param msg The ClientboundSetEntityDataPacket containing the data of the interaction.
     */
    private fun handleDataPacket(player: Player, msg: ClientboundSetEntityDataPacket) {

        val id = msg.getEntityID()

        if (player.entityId == id){

            val list = msg.getSynchedEntityDataList()

            list.filter { it.value is Byte }.forEach {

                when (it.id) {
                    0 -> handleFire(player, it.value as Byte)
                    8 -> handleUsingItem(player, (it.value as Byte).toInt())
                }
            }
        }
        return
    }

    private fun handleUsingItem(player: Player, value: Int) {
        sync {
            Bukkit.getPluginManager().callEvent(
                PlayerUseItemEvent(
                    player,
                    value >= 2,
                    value == 1 || value == 3
                )
            )
        }
    }

    private fun handleFire(player: Player, value: Byte){
        sync {
            if (value == 0.toByte()) {
                player.getMetaDataInfo("onFire")?.also {
                    Bukkit.getPluginManager().callEvent(PlayerExtinguishEvent(player))
                    player.removeMetaData("onFire")
                }
            } else if (value == 1.toByte() && player.getMetaDataInfo("onFire") == null) {
                Bukkit.getPluginManager().callEvent(PlayerIgniteEvent(player))
                player.setMetaData("onFire", true)
            }
        }
    }

    /**
     * Handles the interaction between a player and an NPC.
     *
     * @param msg The ServerboundInteractPacket containing the interaction data.
     */
    private fun handleNPCInteract(msg: ServerboundInteractPacket){

        val firstChar = msg.getActionFirstChar()

        if (firstChar == 'e') { return }

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

