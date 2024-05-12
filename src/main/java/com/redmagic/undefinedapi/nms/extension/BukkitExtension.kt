package com.redmagic.undefinedapi.nms.extension

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.nms.interfaces.entity.NMSPlayer
import com.redmagic.undefinedapi.nms.minecraftVersion.v1_20_4.npc.NMSPlayer1_20_4

fun UndefinedAPI.createFakePlayer(name: String, skinName: String): NMSPlayer?{
    val version = getNMSVersion()
    return when(version){
        "1.20.4" ->{
            NMSPlayer1_20_4(name, skinName)
        }
        else -> null
    }
}