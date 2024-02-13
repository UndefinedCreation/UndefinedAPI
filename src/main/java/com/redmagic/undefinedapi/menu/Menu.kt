package com.redmagic.undefinedapi.menu

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

open class Menu(private val title: String, private val size: Int) {


    val inventory:Inventory;

    val buttons: MutableList<Button> = mutableListOf()

    val movables: MutableList<Int> = mutableListOf()


    constructor(title: String, menuSize: MenuSize = MenuSize.LARGE):this(title, menuSize.size)

    init {
        this.inventory = Bukkit.createInventory(null, size, title)
    }


    fun addButton(button: Button){

        if (isAllReadyButton(button.slot)){

            Bukkit.getLogger().warning("Menu : $title button overwrite. Slot : ${button.slot}")

            buttons.filter { it.slot == button.slot }.forEach { dupeButton: Button ->
                run {
                    buttons.remove(dupeButton)
                }
            }
        }

        buttons.add(button)
    }

    fun setItem(slot: Int, itemStack:ItemStack, movable:Boolean = true){
        if (slot > size){
            Bukkit.getLogger().warning("Menu : $title item outside of menu. Slot : $slot")
            return
        }

        movables.remove(slot)

        inventory.setItem(slot, itemStack)

        if (movable)
            movables.add(slot)

    }

    fun isSlotMoveAble(slot: Int) = movables.any {it == slot}
    private fun isAllReadyButton(slot: Int) = buttons.any { it.slot == slot }

}