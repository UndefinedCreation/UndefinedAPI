package com.redmagic.undefinedapi.nms.extension

import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player


fun Player.getConnection(): ServerGamePacketListenerImpl = (this as CraftPlayer).handle.connection