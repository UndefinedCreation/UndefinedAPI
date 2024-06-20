package com.undefined.api.nms.v1_20_4.extensions

import com.undefined.api.nms.v1_20_4.SpigotNMSMappings
import net.minecraft.network.Connection
import net.minecraft.server.network.ServerCommonPacketListenerImpl

/**
 * Retrieves the connection associated with the instance of ServerCommonPacketListenerImpl.
 *
 * @return the Connection object representing the player's connection
 * @throws NoSuchFieldException if the "c" field does not exist in the ServerCommonPacketListenerImpl class
 * @throws IllegalAccessException if access to the "c" field is denied
 */
fun ServerCommonPacketListenerImpl.getConnection(): Connection {
    val field = ServerCommonPacketListenerImpl::class.java.getDeclaredField(SpigotNMSMappings.ServerCommonPacketListenerImplConnection)
    field.isAccessible = true
    return field[this] as Connection
}