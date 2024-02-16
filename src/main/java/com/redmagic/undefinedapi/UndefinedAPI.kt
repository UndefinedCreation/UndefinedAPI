package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.event.EventManager
import com.redmagic.undefinedapi.menu.MenuManager
import com.redmagic.undefinedapi.scheduler.repeatingTask
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class UndefinedAPI(javaPlugin: JavaPlugin) {

    init {
        plugin = javaPlugin
        MenuManager.setup(plugin)
        EventManager()
    }

    companion object{
        lateinit var plugin: JavaPlugin
    }
}


