package com.redmagic.undefinedapi

import org.bukkit.plugin.java.JavaPlugin

class API(javaPlugin: JavaPlugin) {


    init {
        plugin = javaPlugin
    }

    companion object {
        lateinit var plugin: JavaPlugin
    }

}