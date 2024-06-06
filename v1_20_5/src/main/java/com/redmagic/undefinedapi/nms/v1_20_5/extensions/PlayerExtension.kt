package com.redmagic.undefinedapi.nms.v1_20_5.extensions

import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

/**
 * Sends the given packets to each player in the list.
 *
 * @param packet the packets to send
 *
 * @param Player.sendPacket
 * Sends the given packets to the player.
 *
 * @param packet the packets to send
 *
 * @see Player.getConnection
 */
fun List<Player>.sendPacket(vararg packet: Packet<*>) = forEach { it.sendPacketArray(packet) }
/**
 * Returns the connection to the server for the player.
 *
 * @return the ServerGamePacketListenerImpl object representing the player's connection
 */
fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection
/**
 * Sends the specified packets to the player's connection.
 *
 * @param packet the packets to send
 */
fun Player.sendPacket(vararg packet: Packet<*>) = packet.forEach { getConnection().sendPacket(it) }
/**
 * Sends packets to the player.
 *
 * @param packet the array of packets to be sent
 */
fun Player.sendPacketArray(packet: Array<out Packet<*>>) = packet.forEach { getConnection().sendPacket(it) }

object PlayerExtension {
    /**
     * Retrieves the textures of a player.
     *
     * @param player The player whose textures are to be retrieved.
     * @return An array of strings representing the player's texture and signature.
     */
    fun getTextures(player: Player): Array<String> {
        val cPlayer = player as CraftPlayer
        val sPlayer = cPlayer.handle
        val gameProfile = sPlayer.gameProfile
        val property = gameProfile.properties.get("textures").iterator().next()
        val texture = property.value as String
        val sign = property.signature as String
        return arrayOf(texture, sign)
    }
}

/**
 * Determines whether the current execution environment has remapped the ServerboundSwingPacket class.
 *
 * @return true if the ServerboundSwingPacket class is remapped, false otherwise.
 */
fun isRemapped(): Boolean = ServerboundSwingPacket::class.java.name == "net.minecraft.network.protocol.game.ServerboundSwingPacket"