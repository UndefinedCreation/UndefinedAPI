package com.redmagic.undefinedapi.menu

import com.redmagic.undefinedapi.menu.MenuManager.openMenu
import com.redmagic.undefinedapi.menu.normal.button.ClickData
import com.redmagic.undefinedapi.menu.normal.button.MenuButton
import com.redmagic.undefinedapi.menu.normal.UndefinedMenu
import com.redmagic.undefinedapi.menu.page.UndefinedPageMenu
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

/**
 * Manages the functionality of menus in a plugin.
 *
 * This class provides methods for opening and closing menus,
 * as well as handling menu events such as button clicks and inventory closing.
 * The MenuManager class also stores the currently open menus in a map,
 * with the player's unique ID as the key and the UndefinedMenu object as the value.
 *
 * This class implements the Listener interface to listen for menu events.
 */
object MenuManager : Listener {

    /**
     * Represents a plugin that is used for menu functionality.
     */
    private lateinit var plugin: JavaPlugin

    /**
     * Represents a map of open menus.
     *
     * The key is the UUID of the player who opened the menu,
     * and the value is the UndefinedMenu object representing the menu.
     *
     * @property openMenus The map of open menus.
     */
    val openMenus = HashMap<UUID, UndefinedMenu>()

    /**
     * Sets up the plugin by assigning the provided `JavaPlugin` instance to the `plugin` variable
     * and registering the plugin events with Bukkit.
     *
     * @param plugin the `JavaPlugin` instance of the plugin
     */
    fun setup(plugin: JavaPlugin) {
        this.plugin = plugin
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }


    /**
     * Determines whether the player has a menu open.
     *
     * @return true if the player has a menu open, false otherwise.
     */
    fun Player.hasMenuOpen(): Boolean {
        return openMenus.containsKey(player?.uniqueId)
    }

    /**
     * Opens a menu for the player.
     * If the inventory of the undefined menu is not generated, it generates the inventory using the `generateInventory` method.
     * The menu is then added to the `openMenus` map with the player's unique ID as the key.
     * Finally, the player's inventory is opened with the menu inventory.
     *
     * @param undefinedMenu the undefined menu to be opened
     */
    fun Player.openMenu(undefinedMenu: UndefinedMenu) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (undefinedMenu.inventory == null) undefinedMenu.inventory = undefinedMenu.generateInventory()
            openMenus[player!!.uniqueId] = undefinedMenu
            openInventory(undefinedMenu.inventory!!)
        }, 1)
    }

    /**
     * Closes the menu for the player.
     * If the player does not have a menu open, this method does nothing.
     * If the player has a menu open, it will reset the inventory and remove the player from the list of viewers.
     *
     * @see Player.hasMenuOpen
     * @see Player.getMenu
     * @see onClose
     * @see onLeave
     */
    fun Player.closeMenu(){
        if (!hasMenuOpen()) return
        val menu = getMenu()!!
        if (menu.inventory?.viewers?.size!! >= 0){
            menu.inventory = null
        }
        openMenus.remove(uniqueId)
    }

    /**
     * Retrieves the menu associated with the player.
     *
     * @return The menu object associated with the player.
     */
    fun Player.getMenu() = openMenus[player!!.uniqueId]


    /**
     * Handles the click event in an inventory.
     *
     * @param e The InventoryClickEvent.
     */
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        val player = e.whoClicked as Player
        if (!player.hasMenuOpen()) return
        val menu = player.getMenu()!!
        if (menu is UndefinedPageMenu){
            runPageMenu(menu, e)
        }else{
            runDefaultMenu(menu, e)
        }

    }

    private fun runPageMenu(menu: UndefinedPageMenu, e: InventoryClickEvent){
        val player = e.whoClicked as Player
        if (e.currentItem == null) return
        if (e.currentItem!!.type.isAir) return
        e.isCancelled = true
        if (menu.itemsMap.containsKey(e.rawSlot)) return
        when(e.rawSlot){
            menu.bButton!!.slot ->{
                menu.previousPage()
                return
            }
            menu.nButton!!.slot ->{
                menu.nextPage()
                return
            }
        }
    }

    private fun runDefaultMenu(menu: UndefinedMenu, e: InventoryClickEvent){
        val player = e.whoClicked as Player

        menu.buttons.filter { it.slot == e.slot }.forEach { button ->
            e.isCancelled = true
            button.consumer.invoke(
                ClickData(e.rawSlot, e.whoClicked as Player, e.currentItem, e.click, e.action, e.clickedInventory)
            )
            if (button is MenuButton)
                player.openMenu(button.undefinedMenu)

            return
        }

        if (!menu.movables.contains(e.rawSlot)){
            e.isCancelled = true
        }
    }


    /**
     * Handles the event when an inventory is closed by a player.
     * If the event is triggered by a player, it calls the `closeMenu` function on the player to close their menu.
     *
     * @param e The InventoryCloseEvent representing the closing of an inventory.
     */
    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        if (e.player is Player)
            (e.player as Player).closeMenu()
    }

    /**
     * Handles the event when a player leaves the server.
     *
     * @param e The PlayerQuitEvent representing the event.
     */
    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        e.player.closeMenu()
    }
}