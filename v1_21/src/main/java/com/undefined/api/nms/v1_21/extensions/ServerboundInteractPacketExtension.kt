package com.undefined.api.nms.v1_21.extensions

import com.undefined.api.nms.v1_21.SpigotNMSMappings
import com.undefined.api.nms.extensions.getPrivateField
import net.minecraft.network.protocol.game.ServerboundInteractPacket

/**
 * Retrieves the entity ID from the ServerboundInteractPacket object.
 *
 * @return The entity ID as an integer.
 */
fun ServerboundInteractPacket.getEntityID() = getPrivateField<Int>(SpigotNMSMappings.ServerboundInteractPacketEntityID)

/**
 * Checks if the ServerboundInteractPacket is representing an attack action.
 *
 * @return true if the packet represents an attack action, false otherwise.
 */
fun ServerboundInteractPacket.isAttacking(): Boolean {
    val firstChar = getActionFirstChar()
    return firstChar[0] == '1'
}

/**
 * Gets the action of the ServerboundInteractPacket.
 *
 * @return The action of the packet.
 */
fun ServerboundInteractPacket.getAction() = getPrivateField<Any>(SpigotNMSMappings.ServerboundInteractPacketAction)

/**
 * Determines if the ServerboundInteractPacket represents a main hand interaction.
 *
 * @return true if the interaction is with the main hand, false otherwise.
 */
fun  ServerboundInteractPacket.isMainHand(): Boolean {
    val action = getAction()
    val string = action.getPrivateField<Any>(SpigotNMSMappings.ServerboundInteractPacketActionHand).toString()
    return string == "MAIN_HAND"
}

/**
 * Retrieves the first character of the action string obtained from the `getAction()` method,
 * which represents the first character of the action identifier.
 *
 * @return The first character of the action identifier.
 */
fun ServerboundInteractPacket.getActionFirstChar(): String = getAction().toString().split("$")[1]