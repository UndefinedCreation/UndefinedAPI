package com.undefined.api.menu.normal.button

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * A class that represents a button in a menu.
 *
 * @param slot the slot number where the button should be placed.
 * @param consumer a lambda expression that takes a [ClickData] object as an argument and performs actions based on the click event.
 */
open class Button(open val slot: Int, open val consumer: ClickData.() -> Unit)

/**
 * Represents data related to a click event.
 *
 * @property slot The slot clicked.
 * @property player The player who initiated the click.
 * @property item The item clicked.
 * @property click The type of click.
 * @property action The action performed on the inventory.
 * @property inventory The inventory where the click event occurred.
 */
class ClickData(val slot: Int, val player: Player, val item:ItemStack?, val click:ClickType, val action:InventoryAction, val inventory: Inventory)