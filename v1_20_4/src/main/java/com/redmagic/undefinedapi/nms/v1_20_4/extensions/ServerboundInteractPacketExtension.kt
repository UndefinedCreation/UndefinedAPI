package com.redmagic.undefinedapi.nms.v1_20_4.extensions

import com.redmagic.undefinedapi.nms.v1_20_4.SpigotNMSMappings
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import net.minecraft.network.protocol.game.ServerboundInteractPacket

/**
 * Retrieves the entity ID from the ServerboundInteractPacket.
 *
 * @return The entity ID as an Integer.
 */
fun ServerboundInteractPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ServerboundInteractPacketEntityID)

/**
 * Determines whether the player is attacking.
 *
 * @return true if the player is attacking, false otherwise.
 */
fun ServerboundInteractPacket.isAttacking(): Boolean {
    val action = getAction()
    val firstChar = action.toString().split("$")[1][0]
    return if (isPaper()) this.isAttack else firstChar == '1'
}

/**
 * Retrieves the action associated with the ServerboundInteractPacket.
 *
 * @return the action associated with the packet.
 */
fun ServerboundInteractPacket.getAction() = getPrivateField<Any>(SpigotNMSMappings.ServerboundInteractPacketAction)

/**
 * Checks if the action in the ServerboundInteractPacket is MAIN_HAND.
 *
 * @return true if the action is MAIN_HAND, false otherwise.
 */
fun  ServerboundInteractPacket.isMainHand(): Boolean {
    val action = getAction()
    val string = action.getPrivateField<Any>(SpigotNMSMappings.ServerboundInteractPacketActionHand).toString()
    return string == "MAIN_HAND"
}

/**
 * Retrieves the first character of the action in the ServerboundInteractPacket.
 *
 * @return The first character of the action as a Char.
 */
fun ServerboundInteractPacket.getActionFirstChar(): Char = getAction().toString().split("$")[1][0]