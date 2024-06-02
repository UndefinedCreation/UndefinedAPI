package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.nms.NMSPlayer
import com.redmagic.undefinedapi.nms.PlayerInteract

object NMSManager1_20_6 {

    val npcInteraction: HashMap<NMSPlayer, PlayerInteract.() -> Unit> = hashMapOf()

}