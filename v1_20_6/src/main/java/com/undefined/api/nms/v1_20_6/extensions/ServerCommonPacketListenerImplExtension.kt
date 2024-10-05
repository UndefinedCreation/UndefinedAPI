package com.undefined.api.nms.v1_20_6.extensions

import com.undefined.api.nms.v1_20_6.SpigotNMSMappings
import net.minecraft.network.Connection
import net.minecraft.server.network.ServerCommonPacketListenerImpl

/**
 * Retrieves the connection to the server for the current instance of `ServerCommonPacketListenerImpl`.
 *
 * @return The `Connection` object representing the player's connection to the server.
 */
fun ServerCommonPacketListenerImpl.getConnection(): Connection {
    val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField(SpigotNMSMappings.ServerCommonPacketListenerImplConnection)
    field.isAccessible = true
    return field[this] as Connection
}