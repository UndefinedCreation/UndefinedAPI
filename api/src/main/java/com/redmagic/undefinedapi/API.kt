package com.redmagic.undefinedapi

import org.bukkit.plugin.java.JavaPlugin

class API(javaPlugin: JavaPlugin) {

    companion object {
        lateinit var plugin: JavaPlugin
    }

    init {
        plugin = javaPlugin
    }

}