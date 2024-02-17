package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.page.UndefinedPageMenu
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class TestGUI(itemStack: List<ItemStack>): UndefinedPageMenu("test", MenuSize.LARGE, itemStack) {
    override fun generateInventory() = createInventory {


    }

}