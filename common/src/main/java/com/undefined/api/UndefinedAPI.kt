package com.undefined.api

import com.undefined.api.customEvents.*
import com.undefined.api.menu.MenuManager
import com.undefined.api.nms.NMSManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.yaml.snakeyaml.Yaml
import java.io.File


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
    companion object{
        lateinit var plugin: JavaPlugin
        lateinit var api: UndefinedAPI

        fun adventure(): BukkitAudiences {
            checkNotNull(adventure()) { "Tried to access Adventure when the plugin was disabled!" }
            return adventure()
        }
    }
}


