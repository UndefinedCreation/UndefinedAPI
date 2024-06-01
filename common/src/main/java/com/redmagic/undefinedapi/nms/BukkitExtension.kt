package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.extension.getNMSVersion
import com.redmagic.undefinedapi.npc.NMSPlayer1_20_4
import com.redmagic.undefinedapi.npc.NMSPlayer1_20_5
import com.redmagic.undefinedapi.npc.NMSPlayer1_20_6

fun UndefinedAPI.createFakePlayer(name: String, skinName: String = name): NMSPlayer?{
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> NMSPlayer1_20_4(name, skinName)
        "1.20.5" -> NMSPlayer1_20_5(name, skinName)
        "1.20.6" -> NMSPlayer1_20_6(name, skinName)
        else -> null
    }
}