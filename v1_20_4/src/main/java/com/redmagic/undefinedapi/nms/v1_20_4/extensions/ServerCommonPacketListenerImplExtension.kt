package com.redmagic.undefinedapi.nms.v1_20_4.extensions

import net.minecraft.network.Connection
import net.minecraft.server.network.ServerCommonPacketListenerImpl

fun ServerCommonPacketListenerImpl.getConnection(): Connection {
    val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField("c")
    field.isAccessible = true
    return field.get(this) as Connection
}