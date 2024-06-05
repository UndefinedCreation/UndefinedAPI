package com.redmagic.undefinedapi.nms.v1_20_5.extensions

import com.redmagic.undefinedapi.nms.v1_20_5.SpigotNMSMappings
import net.minecraft.network.Connection
import net.minecraft.server.network.ServerCommonPacketListenerImpl

fun ServerCommonPacketListenerImpl.getConnection(): Connection {
    val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField(SpigotNMSMappings.ServerCommonPacketListenerImplConnection)
    field.isAccessible = true
    return field.get(this) as Connection
}