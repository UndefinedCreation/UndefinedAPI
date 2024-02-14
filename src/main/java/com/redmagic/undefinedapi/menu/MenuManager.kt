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

    val openMenus = HashMap<UUID, UndefinedMenu>()

    fun setup(plugin: JavaPlugin) {
        this.plugin = plugin
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }


    fun Player.hasMenuOpen(): Boolean {
        return openMenus.containsKey(player?.uniqueId)
    }

    fun Player.openMenu(undefinedMenu: UndefinedMenu) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (undefinedMenu.inventory == null) undefinedMenu.inventory = undefinedMenu.generateInventory()
            openMenus[player!!.uniqueId] = undefinedMenu
            openInventory(undefinedMenu.inventory!!)
        }, 1)
    }

    fun Player.closeMenu(){
        if (!hasMenuOpen()) return
        val menu = getMenu()!!
        if (menu.inventory?.viewers?.size!! >= 0){
            menu.inventory = null
        }
        openMenus.remove(uniqueId)
    }

    fun Player.getMenu() = openMenus[player!!.uniqueId]


    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        val player = e.whoClicked as Player
        if (!player.hasMenuOpen()) return
        val menu = player.getMenu()!!
        menu.buttons.filter { it.slot == e.slot }.forEach { button ->
            e.isCancelled = true
            button.consumer.invoke(
                ClickData(e.rawSlot, e.whoClicked as Player, e.currentItem, e.click, e.action, e.clickedInventory)
            )
            return
        }

        if (!menu.movables.contains(e.rawSlot)){
            e.isCancelled = true
        }
    }


    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        if (e.player is Player)
            (e.player as Player).closeMenu()
    }

    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        e.player.closeMenu()
    }
}