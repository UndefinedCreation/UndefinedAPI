package com.redmagic.undefinedapi.nms.v1_20_4.extensions

import com.redmagic.undefinedapi.nms.v1_20_4.SpigotNMSMappings
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.SynchedEntityData

/**
 * Retrieves the entity ID from the ClientboundSetEntityDataPacket.
 *
 * @return The entity ID as an Integer.
 */
fun ClientboundSetEntityDataPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ClientboundSetEntityDataPacketEntityID)

/**
 * Retrieves the list of synchronized entity data values from the ClientboundSetEntityDataPacket.
 *
 * @return The list of synchronized entity data values.
 */
fun ClientboundSetEntityDataPacket.getSynchedEntityDataList() = getPrivateField<List<SynchedEntityData.DataValue<*>>>(
    SpigotNMSMappings.ClientboundSetEntityDataPacketSyncedEntityList)