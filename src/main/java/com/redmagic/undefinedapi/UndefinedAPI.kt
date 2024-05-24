package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.PlayerHitByPlayerManager
import com.redmagic.undefinedapi.customEvents.PlayerMoveManager
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.file.UndefinedYAML
import com.redmagic.undefinedapi.menu.MenuManager
import com.redmagic.undefinedapi.nms.NMSManager
import com.redmagic.undefinedapi.nms.extension.createFakePlayer
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin


/**
 * Represents a class that provides an undefined API.
 *
 * @param javaPlugin The `JavaPlugin` instance of the plugin.
 */
class UndefinedAPI(javaPlugin: JavaPlugin) {

    private var adventure: BukkitAudiences

    init {
        plugin = javaPlugin
        adventure = BukkitAudiences.create(javaPlugin)
        MenuManager.setup(plugin)
        PlayerMoveManager()
        PlayerHitByPlayerManager()
        NMSManager()

    }

    /**
     * Represents a class that provides an undefined API.
     *
     * @property plugin The `JavaPlugin` instance of the plugin.
     */
    companion object{
        lateinit var plugin: JavaPlugin

        fun adventure(): BukkitAudiences {
            checkNotNull(this.adventure()) { "Tried to access Adventure when the plugin was disabled!" }
            return this.adventure()
        }
    }


}


