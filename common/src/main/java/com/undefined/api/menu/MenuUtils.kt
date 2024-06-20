package com.undefined.api.menu

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Checks if the inventory is full.
 *
 * @return True if the inventory is full, false otherwise.
 */
fun Inventory.isFull() = firstEmpty() == -1

/**
 * Returns the number of rows in the inventory.
 *
 * @receiver The inventory to get the rows from.
 *
 * @return The number of rows in the inventory.
 */
fun Inventory.getRows() = size/9

/**
 * Sets the items in a specified row of the inventory.
 *
 * @param row The row number in the inventory.
 * @param item The item to be set in the row.
 */
fun Inventory.setRow(row: Int, item: ItemStack) = setItems(item, ((9*row)until (9*row+9)))
/**
 * Sets the given item to all the specified rows in the inventory.
 *
 * @param item The item to be set.
 * @param rows The rows in the inventory where the item needs to be set.
 */
fun Inventory.setRows(item: ItemStack, vararg rows: Int) = rows.forEach { setRow(it, item) }

/**
 * Sets the items in a specific column of the inventory.
 *
 * @param column the index of the column to set the items in
 * @param item the item to set in the column
 */
fun Inventory.setColumn(column: Int, item: ItemStack) = setItems(item, (column until size step 9))

/**
 * Sets the specified item in the given columns of the inventory.
 *
 * @param item The item to set in the columns.
 * @param columns The columns in which to set the item.
 */
fun Inventory.setColumns(item: ItemStack, vararg columns: Int) = columns.forEach { setColumn(it, item) }

/**
 * Sets the given item in multiple slots of the inventory.
 *
 * @param item The item stack to set in the slots.
 * @param slots The slots in which the item stack should be set.
 */
fun Inventory.setItems(item: ItemStack, vararg slots: Int) = slots.forEach { setItem(it, item) }

/**
 * Sets the specified item in multiple slots of the inventory.
 *
 * @param item The item to set in the slots.
 * @param slots The iterable collection of slots where the item will be set.
 */
fun Inventory.setItems(item: ItemStack, slots: Iterable<Int>) = slots.forEach { setItem(it, item) }

/**
 * Checks if a slot in the inventory is empty.
 *
 * @param slot The slot number to check.
 * @return `true` if the slot is empty, `false` otherwise.
 */
fun Inventory.isSlotEmpty(slot: Int) = getItem(slot)?.type?.isAir != false

/**
 * Sets the border of the inventory to the specified item.
 * The border consists of the first and last rows, and the first and last columns.
 * The item will be set in the specified positions using the setItems function.
 *
 * @param item The ItemStack to set as the border.
 * @return The modified Inventory.
 */
fun Inventory.setBorder(item: ItemStack) = apply {
    setRow(0, item)
    setRow(getRows()-1, item)
    setColumn(0, item)
    setColumn(8, item)
}