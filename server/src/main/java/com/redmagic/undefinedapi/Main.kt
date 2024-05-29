package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.extension.getNMSVersion
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        sendLog(getNMSVersion())

    }
}