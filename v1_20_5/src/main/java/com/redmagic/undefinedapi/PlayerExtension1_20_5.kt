package com.redmagic.undefinedapi

import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player


fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection

fun isRemapped(): Boolean = ServerboundSwingPacket::class.java.name == "net.minecraft.network.protocol.game.ServerboundSwingPacket"