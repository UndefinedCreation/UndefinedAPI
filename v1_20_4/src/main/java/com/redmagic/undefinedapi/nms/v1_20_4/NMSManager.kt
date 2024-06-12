package com.redmagic.undefinedapi.nms.v1_20_4

import com.redmagic.undefinedapi.nms.interfaces.NMSEntity
import com.redmagic.undefinedapi.nms.EntityInteract

/**
 * The NMSManager class provides a centralized management system for NMS players and their interactions.
 * It contains a hashmap that maps NMS players to their corresponding interaction actions.
 *
 * - [entityInteraction]: A hashmap that maps `NMSPlayer` instances to a lambda function representing the player's interaction action.
 */
object NMSManager {

    val entityInteraction: HashMap<NMSEntity, EntityInteract.() -> Unit> = hashMapOf()

}