package com.redmagic.undefinedapi.nms.v1_21.extensions

import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import com.redmagic.undefinedapi.nms.v1_21.SpigotNMSMappings
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket

fun ServerboundSetCarriedItemPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ServerboundSetCarriedItemPacketSlot)