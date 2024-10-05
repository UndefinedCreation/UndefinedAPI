package com.undefined.api.extension

import org.bukkit.Bukkit

fun getNMSVersion(): String = Bukkit.getBukkitVersion().split("-")[0]

