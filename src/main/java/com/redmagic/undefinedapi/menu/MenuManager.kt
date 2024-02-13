package com.redmagic.undefinedapi.menu

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object MenuManager : Listener {

    private lateinit var plugin: JavaPlugin

    val openMenus = HashMap<UUID, Menu>()

    fun getPlugin(): JavaPlugin {
        return this.plugin
    }

    fun setup(plugin: JavaPlugin) {
        this.plugin = plugin
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    fun hasMenuOpen(player: Player): Boolean {
        return openMenus.containsKey(player.uniqueId)
    }

    fun Player.hasMenuOpen(): Boolean {
        return MenuManager.hasMenuOpen(this)
    }

    fun openMenu(player: Player, menu: Menu) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            player.openInventory(menu.inventory)
            openMenus[player.uniqueId] = menu
        }, 1)
    }

    fun Player.openMenu(menu: Menu) {
        MenuManager.openMenu(this, menu)
    }

    fun getMenu(player: Player) = openMenus[player.uniqueId]

    fun Player.getMenu() = MenuManager.getMenu(this)


    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (!hasMenuOpen(e.whoClicked as Player)) return
        val menu = getMenu(e.whoClicked as Player)
        menu?.buttons?.filter { it.slot == e.slot }?.forEach { button ->
            run {
                button.consumer(ClickData(e.rawSlot, e.whoClicked as Player, e.currentItem, e.click, e.action))
            }
        }

        menu?.movables?.contains(e.rawSlot).let {

            e.isCancelled = true
        }
    }


    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        openMenus.remove(e.player.uniqueId)
    }

    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        openMenus.remove(e.player.uniqueId)
    }
}