package com.undefined.api.extension

import org.bukkit.Bukkit

fun getNMSVersion(): String {
    val v = Bukkit.getBukkitVersion()
    return v.split("-")[0]
}

