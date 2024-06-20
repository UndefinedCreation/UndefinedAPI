package com.undefined.api.menu.page.button

import org.bukkit.inventory.ItemStack

/**
 * Represents a button in a page menu.
 *
 * @property slot The slot number where the button should be placed.
 * @property activeButton The active button [ItemStack].
 * @property emptyButton The empty button [ItemStack] to replace the existing item at the slot.
 */
class PageButton(val slot: Int, val activeButton: ItemStack, val emptyButton: ItemStack)