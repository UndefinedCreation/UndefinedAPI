package com.undefined.api.utils

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

fun UUID.getOfflinePlayer(): OfflinePlayer? = Bukkit.getOfflinePlayer(this).takeIf { it.name != null }