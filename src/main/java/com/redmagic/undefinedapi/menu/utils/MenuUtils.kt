package com.redmagic.undefinedapi.menu.utils

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.isFull() = firstEmpty() == -1

fun Inventory.getRows() = size/9

fun Inventory.setRow(row: Int, item: ItemStack) = setItems(item, ((9*row)until (9*row+9)))
fun Inventory.setRows(item: ItemStack, vararg rows: Int) = rows.forEach { setRow(it, item) }

fun Inventory.setColumn(column: Int, item: ItemStack) = setItems(item, (column until size step 9))

fun Inventory.setColumns(item: ItemStack, vararg columns: Int) = columns.forEach { setColumn(it, item) }

fun Inventory.setItems(item: ItemStack, vararg slots: Int) = slots.forEach { setItem(it, item) }

fun Inventory.setItems(item: ItemStack, slots: Iterable<Int>) = slots.forEach { setItem(it, item) }

fun Inventory.isSlotEmpty(slot: Int) = getItem(slot)?.type?.isAir != false

fun Inventory.setBorder(item: ItemStack) = apply {
    setRow(0, item)
    setRow(getRows()-1, item)
    setColumn(0, item)
    setColumn(8, item)
}