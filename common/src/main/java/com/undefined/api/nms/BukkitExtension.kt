package com.undefined.api.nms

import com.undefined.api.UndefinedAPI
import com.undefined.api.extension.getNMSVersion
import com.undefined.api.nms.interfaces.NMSPlayer
import org.bukkit.entity.Player

@Deprecated("Use createFakeEntity instead")
fun UndefinedAPI.createFakePlayer(name: String, skinName: String = name): NMSPlayer?{
    val version = getNMSVersion()
    return when(version) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.npc.NMSPlayer(name, skinName)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.npc.NMSPlayer(name, skinName)
        "1.21", "1.21.1" -> com.undefined.api.nms.v1_21.npc.NMSPlayer(name, skinName)
        else -> null
    }
}

@Deprecated("Use createFakeEntity instead")
fun UndefinedAPI.createFakePlayer(name: String, texture: String, sign: String): NMSPlayer?{
    val version = getNMSVersion()
    return when(version) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.npc.NMSPlayer(name, texture, sign)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.npc.NMSPlayer(name, texture, sign)
        "1.21", "1.21.1" -> com.undefined.api.nms.v1_21.npc.NMSPlayer(name, texture, sign)
        else -> null
    }
}

fun Player.getTexture(): Array<String> {
    val version = getNMSVersion()
    return when(version) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.PlayerExtension.getTextures(this)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.PlayerExtension.getTextures(this)
        else -> arrayOf("","")
    }
}

fun Player.triggerTotem(): Boolean {
    val version = getNMSVersion()
    return when(version) {
        "1.20.4" -> com.undefined.api.nms.v1_20_4.extensions.PlayerExtension.triggerTotem(this)
        "1.20.6" -> com.undefined.api.nms.v1_20_6.extensions.PlayerExtension.triggerTotem(this)
        "1.21", "1.21.1" -> com.undefined.api.nms.v1_21.extensions.PlayerExtension.triggerTotem(this)
        else -> false
    }
}