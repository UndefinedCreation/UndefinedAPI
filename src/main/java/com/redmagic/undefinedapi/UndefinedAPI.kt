package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.menu.MenuManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

class UndefinedAPI(javaPlugin: JavaPlugin) {

    init {
        plugin = javaPlugin
        MenuManager.setup(plugin)
    }

    companion object{
        lateinit var plugin: JavaPlugin
    }

}
