package com.redmagic.undefinedapi.nms.extension

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.redmagic.undefinedapi.nms.interfaces.entity.NMSPlayer
import java.io.InputStreamReader
import java.net.URL


fun NMSPlayer.toDeltaValue(value: Double, newValue: Double) = (((newValue - value) * 32 * 128).toInt().toShort())

fun NMSPlayer.toRotationValue(rotation: Float) = (((rotation % 360) * 256 / 360).toInt().toByte())

fun NMSPlayer.getSkinTexture(name: String): Array<String> {

    val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
    val reader = InputStreamReader(url.openStream())
    val uuid = JsonParser().parse(reader).asJsonObject["id"].asString

    val url1 = URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false")
    val reader1 = InputStreamReader(url1.openStream())
    val textureProperty: JsonObject = JsonParser().parse(reader1).asJsonObject.get("properties").asJsonArray.get(0).asJsonObject
    val texture = textureProperty["value"].asString
    val signature = textureProperty["signature"].asString

    return arrayOf(texture, signature)
}
