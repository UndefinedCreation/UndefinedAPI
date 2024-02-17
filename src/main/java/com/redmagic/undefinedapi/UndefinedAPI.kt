package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.menu.MenuManager
import com.redmagic.undefinedapi.scheduler.TimeUnit
import com.redmagic.undefinedapi.scheduler.delay
import com.redmagic.undefinedapi.scheduler.repeatingTask
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin


class UndefinedAPI(javaPlugin: JavaPlugin) {

    init {
        plugin = javaPlugin
        MenuManager.setup(plugin)


    }

    companion object{
        lateinit var plugin: JavaPlugin
    }
}


