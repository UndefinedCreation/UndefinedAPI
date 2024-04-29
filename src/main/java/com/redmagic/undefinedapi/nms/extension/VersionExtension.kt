package com.redmagic.undefinedapi.nms.extension

import org.bukkit.Bukkit

fun getNMSVersion(): String {
    val v = Bukkit.getServer().javaClass.`package`.name
    return when(v.substring(v.lastIndexOf('.') + 1)){
        "v1_20_R3" -> "1.20.4"
        else -> "-1_20_3"
    }
}