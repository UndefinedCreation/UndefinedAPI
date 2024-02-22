package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.PlayerMoveEvent
import com.redmagic.undefinedapi.customEvents.PlayerMoveManager
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.menu.MenuManager
import com.redmagic.undefinedapi.scheduler.TimeUnit
import com.redmagic.undefinedapi.scheduler.delay
import com.redmagic.undefinedapi.scheduler.repeatingTask
import com.redmagic.undefinedapi.scoreboard.UndefinedScoreboard
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin


/**
 * Represents a class that provides an undefined API.
 *
 * @param javaPlugin The `JavaPlugin` instance of the plugin.
 */
class UndefinedAPI(javaPlugin: JavaPlugin) {

    init {
        plugin = javaPlugin
        MenuManager.setup(plugin)
        PlayerMoveManager()
    }

    /**
     * Represents a class that provides an undefined API.
     *
     * @property plugin The `JavaPlugin` instance of the plugin.
     */
    companion object{
        lateinit var plugin: JavaPlugin
    }
}


