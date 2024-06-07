package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.*
import com.redmagic.undefinedapi.menu.MenuManager
import com.redmagic.undefinedapi.nms.NMSManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.configuration.file.YamlConfiguration
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
        API(plugin)
        adventure = BukkitAudiences.create(javaPlugin)
        MenuManager.setup(plugin)
        PlayerMoveManager()
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

        fun adventure(): BukkitAudiences {
            checkNotNull(adventure()) { "Tried to access Adventure when the plugin was disabled!" }
            return adventure()
        }
    }
}


