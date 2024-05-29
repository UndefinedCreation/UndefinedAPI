package com.redmagic.undefinedapi.extension

import org.bukkit.Bukkit

fun getNMSVersion(): String {
    val v = Bukkit.getBukkitVersion()
    return v.split("-")[0]
}

