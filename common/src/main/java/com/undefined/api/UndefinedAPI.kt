package com.undefined.api

import com.undefined.api.customEvents.block.BlockProgressManager
import com.undefined.api.customEvents.entity.player.PlayerArmorChangeManager
import com.undefined.api.customEvents.entity.player.PlayerHitByPlayerManager
import com.undefined.api.menu.MenuManager
import com.undefined.api.nms.NMSManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
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
        api = this
        API(plugin)
        adventure = BukkitAudiences.create(javaPlugin)
        MenuManager.setup(plugin)
        PlayerHitByPlayerManager()
        BlockProgressManager()
        PlayerArmorChangeManager()
        NMSManager()
    }

    /**
     * Represents a class that provides an undefined API.
     *
     * @property plugin The `JavaPlugin` instance of the plugin.
     */
    companion object {
        lateinit var plugin: JavaPlugin
        lateinit var api: UndefinedAPI

        fun adventure(): BukkitAudiences {
            checkNotNull(adventure()) { "Tried to access Adventure when the plugin was disabled!" }
            return adventure()
        }
    }
}


