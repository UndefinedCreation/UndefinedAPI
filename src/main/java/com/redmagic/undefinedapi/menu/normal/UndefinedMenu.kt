package com.redmagic.undefinedapi.menu.normal

import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.button.Button
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * An abstract class representing an undefined menu.
 *
 * @param title The title of the menu.
 * @param size The size of the menu.
 */
abstract class UndefinedMenu(private val title: String, private val size: Int) {

    /**
     * Represents the inventory of an undefined menu.
     *
     * @property buttons The list of buttons associated with the inventory.
     * @property movables The list of movable slots in the inventory.
     */
    var inventory: Inventory? = null

    /**
     * Represents a collection of buttons in a menu.
     */
    val buttons: MutableList<Button> = mutableListOf()

    /**
     * Represents a list of movables.
     *
     * This list contains integers representing the slots in an inventory that are movable. The movables are used in the context of a menu, where items can be moved or interacted
     * with by the player.
     * The list can be modified by adding or removing elements.
     *
     * @see UndefinedMenu
     * @see UndefinedMenu.createInventory
     * @see UndefinedMenu.setItem
     * @see UndefinedMenu.onClick
     * @see Button
     */
    val movables: MutableList<Int> = mutableListOf()




    /**
     * Constructs a Menu object with the given title and menu size.
     *
     * @param title the title of the menu
     * @param menuSize the size of the menu, default value is MenuSize.LARGE
     */
    constructor(title: String, menuSize: MenuSize = MenuSize.LARGE):this(title, menuSize.size)

    /**
     * Sets an item in the inventory at the specified slot. If the slot is outside the
     * inventory size, a warning message is logged and the method returns without
     * performing any action.
     *
     * @param slot The slot number where the item should be set.
     * @param itemStack The ItemStack to set in the slot.
     * @param movable Whether the item is movable or not. Defaults to false.
     */
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

    /**
     * Adds a button to the inventory.
     *
     * @param button the button to be added.
     */
    fun Inventory.addButton(button: Button){
        buttons.add(button)
    }

    /**
     * Fills the empty slots of the inventory with the given item stack.
     *
     * @param itemStack The item stack to fill the empty slots with.
     * @param movable Indicates whether the filled slots should be movable.
     */
    fun Inventory.fillEmpty(itemStack: ItemStack, movable: Boolean = true){
        while (firstEmpty() != -1){
            setItem(firstEmpty(), itemStack, movable)
        }
    }


    /**
     * Creates an inventory with buttons and movables based on the provided display function.
     *
     * @param display a lambda expression that configures the inventory by adding buttons and setting items.
     *                It takes an instance of [Inventory] as the receiver and does not return a value.
     * @return the created [Inventory] object.
     */
    open fun createInventory(display: Inventory.() -> Unit) : Inventory {
        buttons.clear()
        movables.clear()
        val inventory = Bukkit.createInventory(null, size, title).apply(display)
        return inventory
    }

    /**
     * Generates the inventory for the UndefinedMenu.
     * This method should be implemented by subclasses of UndefinedMenu to generate the specific inventory for that menu.
     * The generated inventory should be returned as the result.
     *
     * @return the generated inventory
     */
    abstract fun generateInventory(): Inventory

}