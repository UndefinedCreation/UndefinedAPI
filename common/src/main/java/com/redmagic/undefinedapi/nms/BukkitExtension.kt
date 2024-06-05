package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.extension.getNMSVersion
import com.redmagic.undefinedapi.nms.v1_20_4.extensions.PlayerExtension
import org.bukkit.entity.Player

fun UndefinedAPI.createFakePlayer(name: String, skinName: String = name): NMSPlayer?{
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.npc.NMSPlayer(name, skinName)
        "1.20.5" -> com.redmagic.undefinedapi.nms.v1_20_5.npc.NMSPlayer(name, skinName)
        "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_6.npc.NMSPlayer(name, skinName)
        else -> null
    }
}

fun UndefinedAPI.createFakePlayer(name: String, texture: String, sign: String): NMSPlayer?{
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.npc.NMSPlayer(name, texture, sign)
        "1.20.5" -> com.redmagic.undefinedapi.nms.v1_20_5.npc.NMSPlayer(name, texture, sign)
        "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_6.npc.NMSPlayer(name, texture, sign)
        else -> null
    }
}

fun Player.getTexture(): Array<String> {
    val version = getNMSVersion()
    return when(version){
        "1.20.4" -> PlayerExtension.getTextures(this)
        "1.20.5" -> com.redmagic.undefinedapi.nms.v1_20_5.extensions.PlayerExtension.getTextures(this)
        "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_6.extensions.PlayerExtension.getTextures(this)
        else -> arrayOf("","")
    }
}