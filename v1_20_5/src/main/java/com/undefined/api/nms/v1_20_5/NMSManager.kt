package com.undefined.api.nms.v1_20_5

import com.undefined.api.nms.EntityInteract
import com.undefined.api.nms.interfaces.NMSEntity

/**
 * The NMSManager class provides a centralized management system for NMS players and their interactions.
 * It contains a hashmap that maps NMS players to their corresponding interaction actions.
 *
 * - [entityInteraction]: A hashmap that maps `NMSPlayer` instances to a lambda function representing the player's interaction action.
 */
object NMSManager {

    val entityInteraction: HashMap<com.undefined.api.nms.interfaces.NMSEntity, EntityInteract.() -> Unit> = hashMapOf()

}