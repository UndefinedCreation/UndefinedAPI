package com.undefined.api.nms.v1_21.extensions

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_21.SpigotNMSMappings
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket

fun ServerboundSetCarriedItemPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ServerboundSetCarriedItemPacketSlot)