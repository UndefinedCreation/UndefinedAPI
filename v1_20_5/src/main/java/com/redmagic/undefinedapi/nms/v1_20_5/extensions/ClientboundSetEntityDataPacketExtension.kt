package com.redmagic.undefinedapi.nms.v1_20_5.extensions

import com.redmagic.undefinedapi.nms.v1_20_5.SpigotNMSMappings
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.SynchedEntityData

fun ClientboundSetEntityDataPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ClientboundSetEntityDataPacketEntityID)

fun ClientboundSetEntityDataPacket.getSynchedEntityDataList() = getPrivateField<List<SynchedEntityData.DataValue<*>>>(
    SpigotNMSMappings.ClientboundSetEntityDataPacketSyncedEntityList)