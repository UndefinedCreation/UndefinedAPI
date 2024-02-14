package com.redmagic.undefinedapi.menu

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class Menu(private val title: String, private val size: Int) {

    var inventory: Inventory? = null

    val buttons: MutableList<Button> = mutableListOf()

    val movables: MutableList<Int> = mutableListOf()




    constructor(title: String, menuSize: MenuSize = MenuSize.LARGE):this(title, menuSize.size)

    fun Inventory.setItem(slot: Int, itemStack:ItemStack, movable: Boolean = false){
        if (slot > size){
            Bukkit.getLogger().warning("Menu : $title item outside of menu. Slot : $slot")
            return
        }

        movables.remove(slot)

        this.setItem(slot, itemStack)

        if (!movable)
            movables.add(slot)

    }

    fun Inventory.addButton(button: Button){
        buttons.add(button)
    }

    fun Inventory.fillEmpty(itemStack: ItemStack, movable: Boolean = true){
        while (firstEmpty() != -1){
            setItem(firstEmpty(), itemStack, movable)
        }
    }


    fun createInventory(display: Inventory.() -> Unit) : Inventory {
        buttons.clear()
        movables.clear()
        val inventory = Bukkit.createInventory(null, size, title).apply(display)
        return inventory
    }

    abstract fun generateInventory(): Inventory

}