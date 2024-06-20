package com.undefined.api.nms.interfaces

import org.bukkit.inventory.ItemStack

interface NMSItemEntity: com.undefined.api.nms.interfaces.NMSEntity {

    var itemStack: ItemStack

    fun update()

}