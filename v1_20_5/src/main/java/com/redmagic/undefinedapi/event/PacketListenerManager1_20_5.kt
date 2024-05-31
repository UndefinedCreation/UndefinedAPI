package com.redmagic.undefinedapi.event

import com.redmagic.undefinedapi.customEvents.PlayerArmSwingEvent
import com.redmagic.undefinedapi.getConnection
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import net.minecraft.world.InteractionHand
import org.bukkit.Bukkit
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
class PacketListenerManager1_20_5 {

    init {

        event<PlayerJoinEvent> {

            val channelDuplexHandler = object : ChannelDuplexHandler() {
                override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {

                    if (msg is ServerboundSwingPacket){
                        val event = PlayerArmSwingEvent(player, if (msg.hand.equals(InteractionHand.MAIN_HAND)) EquipmentSlot.HAND else EquipmentSlot.OFF_HAND)
                        Bukkit.getPluginManager().callEvent(event)
                        if (event.isCancelled) return
                    }

                    super.channelRead(ctx, msg)
                }

                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                    super.write(ctx, msg, promise)
                }
            }

            val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField("e")
            field.isAccessible = true
            val fakeConnection = field.get(player.getConnection()) as Connection

            val channel = fakeConnection.channel
            val pipeline = channel.pipeline()
            pipeline.addBefore("packet_handler", "${player.uniqueId}_UNDEFINEDAPI", channelDuplexHandler)

        }

        event<PlayerQuitEvent> {
            val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField("e")
            field.isAccessible = true
            val connection = field.get(player.getConnection()) as Connection
            val channel = connection.channel
            channel.eventLoop().submit(){
                channel.pipeline().remove("${player.uniqueId}_UNDEFINEDAPI")
            }
        }

    }

}