package com.undefined.api.nms.v1_20_4.extensions

import com.mojang.authlib.GameProfile
import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_20_4.SpigotNMSMappings
import net.minecraft.network.protocol.Packet
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player


/**
 * Sends a packet to each player in the list.
 *
 * @param packet the packet to send
 */
fun List<Player>.sendPacket(vararg packet: Packet<*>) = forEach { it.sendPacketArray(packet) }
/**
 * Retrieves the connection associated with the player.
 *
 * @return the ServerGamePacketListenerImpl representing the player's connection
 */
fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection
/**
 * Sends one or more packets to the player's connection.
 *
 * @param packet the packet(s) to send
 */
fun Player.sendPacket(vararg packet: Packet<*>) = packet.forEach { getConnection().send(it) }
/**
 * Sends an array of packets to the player.
 *
 * @param packet the array of packets to be sent
 */
fun Player.sendPacketArray(packet: Array<out Packet<*>>) = packet.forEach { getConnection().send(it) }

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
        val gameProfile = sPlayer.getGameProfile()
        val property = gameProfile.properties["textures"].iterator().next()
        val texture = property.value as String
        val sign = property.signature as String

        return arrayOf(texture, sign)
    }
}


/**
 * Checks if the server is running on Paper.
 *
 * @return true if the server is running on Paper, false otherwise.
 */
fun isPaper(): Boolean {
    val hasClass = { name: String -> try { Class.forName(name); true } catch (e: ClassNotFoundException) { false } }

    return hasClass("com.destroystokyo.paper.PaperConfig") || hasClass("io.papermc.paper.configuration.Configuration")
}