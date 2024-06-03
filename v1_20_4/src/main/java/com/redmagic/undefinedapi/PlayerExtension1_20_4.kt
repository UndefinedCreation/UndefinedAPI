package com.redmagic.undefinedapi

import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player


fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection

object PlayerExtension1_20_4 {
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


fun isPaper(): Boolean {
    val hasClass = { name: String -> try { Class.forName(name); true } catch (e: ClassNotFoundException) { false } }

    return if (hasClass("com.destroystokyo.paper.PaperConfig") || hasClass("io.papermc.paper.configuration.Configuration")) true else false
}