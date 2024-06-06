package com.redmagic.undefinedapi.nms.v1_20_5

import com.redmagic.undefinedapi.nms.NMSPlayer
import com.redmagic.undefinedapi.nms.PlayerInteract

/**
 * The `NMSManager` class is responsible for managing the interaction between players and non-player characters (NPCs) in the game.
 * It maintains a map of `NMSPlayer` instances to player interaction functions.
 *
 * @property npcInteraction A map that associates an `NMSPlayer` instance with a function that defines the interaction logic between the player and the NPC.
 */
object NMSManager {

    val npcInteraction: HashMap<NMSPlayer, PlayerInteract.() -> Unit> = hashMapOf()

}