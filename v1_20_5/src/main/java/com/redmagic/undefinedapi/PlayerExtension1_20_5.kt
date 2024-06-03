package com.redmagic.undefinedapi

import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player


fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection

object PlayerExtension1_20_5 {
    fun getTextures(player: Player): Array<out String> {
        val cPlayer = player as CraftPlayer
        val sPlayer = cPlayer.handle
        val gameProfile = sPlayer.gameProfile
        val property = gameProfile.properties.get("textures").iterator().next()
        val texture = property.value as String
        val sign = property.signature as String
        return arrayOf(texture, sign)
    }
}

fun isRemapped(): Boolean = ServerboundSwingPacket::class.java.name == "net.minecraft.network.protocol.game.ServerboundSwingPacket"