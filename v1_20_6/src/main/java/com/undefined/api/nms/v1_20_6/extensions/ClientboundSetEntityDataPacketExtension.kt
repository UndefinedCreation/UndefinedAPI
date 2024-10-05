package com.undefined.api.nms.v1_20_6.extensions

import com.undefined.api.nms.v1_20_6.SpigotNMSMappings
import com.undefined.api.nms.extensions.getPrivateField
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.SynchedEntityData

/**
 * Retrieves the entity ID from the ClientboundSetEntityDataPacket object.
 *
 * @return The entity ID as an integer.
 */
fun ClientboundSetEntityDataPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ClientboundSetEntityDataPacketEntityID)

/**
 * Retrieves the list of synchronized entity data from the ClientboundSetEntityDataPacket.
 *
 * @return The list of synchronized entity data.
 */
fun ClientboundSetEntityDataPacket.getSynchedEntityDataList() = getPrivateField<List<SynchedEntityData.DataValue<*>>>(SpigotNMSMappings.ClientboundSetEntityDataPacketSyncedEntityList)