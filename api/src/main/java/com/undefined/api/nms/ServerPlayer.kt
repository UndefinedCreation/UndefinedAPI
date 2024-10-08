package com.undefined.api.nms

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.undefined.api.nms.interfaces.NMSLivingEntity
import com.undefined.api.nms.interfaces.NMSPlayer
import org.bukkit.Bukkit
import java.io.InputStreamReader
import java.net.URI
import kotlin.math.floor

fun NMSLivingEntity.toDeltaValue(value: Double, newValue: Double) = (((newValue - value) * 32 * 128).toInt().toShort())

fun NMSLivingEntity.toRotationValue(rotation: Float): Byte = floor(rotation * 256.0f / 360.0f).toInt().toByte()

fun NMSPlayer.getSkinTexture(name: String): Array<String> {

    val textureProperty: JsonObject
    var texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTcxNTMxMjI5MCwKICAicHJvZmlsZUlkIiA6ICJiNTM5NTkyMjMwY2I0MmE0OWY5YTRlYmYxNmRlOTYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJpYW5hZmFnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFhNGFmNzE4NDU1ZDRhYWI1MjhlN2E2MWY4NmZhMjVlNmEzNjlkMTc2OGRjYjEzZjdkZjMxOWE3MTNlYjgxMGIiCiAgICB9CiAgfQp9"
    var signature = "otpbxDm9B+opW7jEzZF8BVDeZSqaqdF0dyLlnlyMh7Q5ysJFDL48/9J/IOHp8JqNm1oarmVdvxrroy9dlNI2Mz4BVuJM2pcCOJwk2h+aZ4dzNZGxst+MYNPSw+i4sMoYu7OV07UVHrQffolFF7MiaBUst1hFwM07IpTE6UtIQz4rqWisXe9Iz5+ooqX4wj0IB3dPntsh6u5nVlL8acWCBDAW4YqcPt2Y4CKK+KtskjzusjqGAdEO+4lRcW1S0ldo2RNtUHEzZADWQcADjg9KKiKq9QIpIpYURIoIAA+pDGb5Q8L5O6CGI+i1+FxqXbgdBvcm1EG0OPdw9WpSqAxGGeXSwlzjILvlvBzYbd6gnHFBhFO+X7iwRJYNd+qQakjUa6ZwR8NbkpbN3ABb9+6YqVkabaEmgfky3HdORE+bTp/AT6LHqEMQo0xdNkvF9gtFci7RWhFwuTLDvQ1esby1IhlgT+X32CPuVHuxEvPCjN7+lmRz2OyOZ4REo2tAIFUKakqu3nZ0NcF98b87wAdA9B9Qyd2H/rEtUToQhpBjP732Sov6TlJkb8echGYiLL5bu/Q7hum72y4+j2GNnuRiOJtJidPgDqrYMg81GfenfPyS6Ynw6KhdEhnwmJ1FJlJhYvXZyqZwLAV1c26DNYkrTMcFcv3VXmcd5/2Zn9FnZtw="

    try {
        val url = URI("https://api.mojang.com/users/profiles/minecraft/$name").toURL()
        url.openStream().use { inputStream ->   // Auto close InputStream
            InputStreamReader(inputStream).use { reader -> // Auto close InputStreamReader
                val uuid = JsonParser.parseReader(reader).asJsonObject["id"].asString

                val url1 = URI("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false").toURL()
                url1.openStream().use { inputStream1 -> // Auto close InputStream
                    InputStreamReader(inputStream1).use { reader1 -> // Auto close InputStreamReader
                        textureProperty = JsonParser.parseReader(reader1).asJsonObject.get("properties").asJsonArray.get(0).asJsonObject
                        texture = textureProperty["value"].asString ?: texture
                        signature = textureProperty["signature"].asString ?: signature
                    }
                }
            }
        }
    } catch (e: Exception) {
        Bukkit.getLogger().severe("Failed to get texture: ${e.message}")
    }

    return arrayOf(texture, signature)
}
