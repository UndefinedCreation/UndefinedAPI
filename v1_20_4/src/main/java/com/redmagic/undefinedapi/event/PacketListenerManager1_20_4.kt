package com.redmagic.undefinedapi.event

import com.redmagic.undefinedapi.NMSManager1_20_4
import com.redmagic.undefinedapi.customEvents.PlayerArmSwingEvent
import com.redmagic.undefinedapi.customEvents.PlayerExtinguishEvent
import com.redmagic.undefinedapi.customEvents.PlayerIgniteEvent
import com.redmagic.undefinedapi.exstions.getMetaDataInfo
import com.redmagic.undefinedapi.exstions.removeMetaData
import com.redmagic.undefinedapi.exstions.setMetaData
import com.redmagic.undefinedapi.getConnection
import com.redmagic.undefinedapi.isPaper
import com.redmagic.undefinedapi.nms.ClickType
import com.redmagic.undefinedapi.nms.PlayerInteract
import com.redmagic.undefinedapi.scheduler.sync
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ServerboundInteractPacket
import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import net.minecraft.world.InteractionHand
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue
import org.bukkit.plugin.java.JavaPlugin

/**
 * This class represents a packet listener for Minecraft version 1.20.4.
 * It listens for specific events, such as when a player joins or quits the server,
 * and performs certain actions when those events occur.
 *
 * @constructor Creates an instance of PacketListenerManager1_20_4.
 */
class PacketListenerManager1_20_4 {

    init {

        event<PlayerJoinEvent> {

            if (player.fireTicks > 0) {
                player.setMetaData("onFire", true)
            }

            val channelDuplexHandler = object : ChannelDuplexHandler() {
                override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {

                    if (msg == null) return

                    when (msg::class) {
                        ServerboundSwingPacket::class.java -> {
                            msg as ServerboundSwingPacket
                            val event = PlayerArmSwingEvent(player, if (msg.hand.equals(InteractionHand.MAIN_HAND)) EquipmentSlot.HAND else EquipmentSlot.OFF_HAND)
                            Bukkit.getPluginManager().callEvent(event)
                            if (event.isCancelled) return
                        }
                        ServerboundInteractPacket::class.java -> {
                            msg as ServerboundInteractPacket
                            handleNPCInteract(msg)
                        }
                    }

                    super.channelRead(ctx, msg)
                }

                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {

                    if (msg == null) {
                        super.write(ctx, msg, promise);
                        return
                    }

                     sync {
                         when (msg::class) {
                             ClientboundSetEntityDataPacket::class -> {
                                 msg as ClientboundSetEntityDataPacket
                                 handleFire(player, msg)
                             }
                         }
                     }

                    super.write(ctx, msg, promise)
                }
            }


            val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField("c")
            field.isAccessible = true
            val fakeConnection = field.get(player.getConnection()) as Connection

            val channel = fakeConnection.channel
            val pipeline = channel.pipeline()
            pipeline.addBefore("packet_handler", "${player.uniqueId}_UNDEFINEDAPI", channelDuplexHandler)

        }

        event<PlayerQuitEvent> {

            player.removeMetaData("onFire")

            val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField("c")
            field.isAccessible = true
            val connection = field.get(player.getConnection()) as Connection
            val channel = connection.channel
            channel.eventLoop().submit(){
                channel.pipeline().remove("${player.uniqueId}_UNDEFINEDAPI")
            }
        }

    }

    fun handleFire(player: Player, msg: ClientboundSetEntityDataPacket) {

        val entityIDField = ClientboundSetEntityDataPacket::class.java.getDeclaredField("b")
        entityIDField.isAccessible = true
        val id = entityIDField.get(msg) as Int

        if (player.entityId == id){


            val listField = ClientboundSetEntityDataPacket::class.java.getDeclaredField("c")
            listField.isAccessible = true

            val list: List<SynchedEntityData.DataValue<*>> = listField.get(msg) as List<SynchedEntityData.DataValue<*>>

            list.forEach {
                if (it.id == 0){
                    if (it.value is Byte){
                        if (it.value == 0.toByte()){
                            player.getMetaDataInfo("onFire")?.also {
                                val event = PlayerExtinguishEvent(player)
                                Bukkit.getPluginManager().callEvent(event)
                                player.removeMetaData("onFire")
                            }
                        }else if (it.value == 1.toByte()){
                            val event = PlayerIgniteEvent(player)
                            Bukkit.getPluginManager().callEvent(event)
                            player.setMetaData("onFire", true)
                        }
                    }
                }

            }

        }

        return
    }

    fun handleNPCInteract(msg: ServerboundInteractPacket){
        val actionID = ServerboundInteractPacket::class.java.getDeclaredField("b")
        actionID.isAccessible = true
        val ob: Any = actionID.get(msg)
        val firstChar = ob.toString().split("$")[1][0]
        val attacking = if (isPaper()) msg.isAttack else firstChar == '1'

        if (firstChar == 'e') { return }

        if (!attacking){

            val handField = ob.javaClass.getDeclaredField("a")
            handField.isAccessible = true
            val string = handField.get(ob).toString()
            if (string != "MAIN_HAND") { return }
        }

        val idField = ServerboundInteractPacket::class.java.getDeclaredField("a")
        idField.isAccessible = true
        val id = idField.get(msg) as Int
        val action = if(attacking) ClickType.LEFT_CLICK else ClickType.RIGHT_CLICK

        NMSManager1_20_4.npcInteraction.entries.forEach {
            if (it.key.getEntityID() == id) {
                it.value.invoke(PlayerInteract(action, it.key))
            }
        }
    }

}

