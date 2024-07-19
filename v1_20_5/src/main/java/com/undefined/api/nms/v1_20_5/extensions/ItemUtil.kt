package com.undefined.api.nms.v1_20_5.extensions

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property

import org.bukkit.inventory.meta.SkullMeta
import java.util.*

object ItemUtil {
    fun setSkullTexture(meta: SkullMeta, textures: String): SkullMeta {
        val gameP = GameProfile(UUID.randomUUID(), "texture")
        gameP.properties.put("textures", Property("textures", textures))
        val field = meta::class.java.getDeclaredField("profile")
        field.isAccessible = true
        field.set(meta, gameP)
        return meta
    }
}