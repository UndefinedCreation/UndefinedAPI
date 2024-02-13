package com.redmagic.undefinedapi.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class Button(val slot:Int, val consumer: (ClickData) -> Unit)

class ClickData(val slot: Int, val player: Player?, val item:ItemStack?, val click:ClickType, val action:InventoryAction)