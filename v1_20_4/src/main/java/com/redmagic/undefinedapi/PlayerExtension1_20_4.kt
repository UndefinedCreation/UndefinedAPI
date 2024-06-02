package com.redmagic.undefinedapi

import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player


fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection

fun isPaper(): Boolean {
    val hasClass = { name: String -> try { Class.forName(name); true } catch (e: ClassNotFoundException) { false } }

    return if (hasClass("com.destroystokyo.paper.PaperConfig") || hasClass("io.papermc.paper.configuration.Configuration")) true else false
}