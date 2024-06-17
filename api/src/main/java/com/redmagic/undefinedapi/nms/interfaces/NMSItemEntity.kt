package com.redmagic.undefinedapi.nms.interfaces

import org.bukkit.inventory.ItemStack

interface NMSItemEntity: NMSEntity {

    var itemStack: ItemStack

    fun update()

}