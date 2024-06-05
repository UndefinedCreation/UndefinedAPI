package com.redmagic.undefinedapi.nms.v1_20_5.extensions

import com.redmagic.undefinedapi.nms.v1_20_5.SpigotNMSMappings
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import net.minecraft.network.protocol.game.ServerboundInteractPacket

fun ServerboundInteractPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ServerboundInteractPacketEntityID)

fun ServerboundInteractPacket.isAttacking(): Boolean {
    val firstChar = getActionFirstChar()
    return if(isRemapped()) this.isAttack else firstChar == '1'
}

fun ServerboundInteractPacket.getAction() = getPrivateField<Any>(SpigotNMSMappings.ServerboundInteractPacketAction)

fun  ServerboundInteractPacket.isMainHand(): Boolean {
    val action = getAction()
    val string = action.getPrivateField<Any>(SpigotNMSMappings.ServerboundInteractPacketActionHand).toString()
    return string == "MAIN_HAND"
}

fun ServerboundInteractPacket.getActionFirstChar(): Char = getAction().toString().split("$")[1][0]