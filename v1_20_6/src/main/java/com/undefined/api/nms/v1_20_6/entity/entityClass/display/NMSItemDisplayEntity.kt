package com.undefined.api.nms.v1_20_6.entity.entityClass.display

import com.undefined.api.nms.interfaces.display.ItemDisplayContext
import com.undefined.api.nms.interfaces.display.NMSItemDisplayEntity
import com.undefined.api.nms.v1_20_6.extensions.DATA_ITEM_DISPLAY_ID
import net.minecraft.world.entity.Display.ItemDisplay
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

class NMSItemDisplayEntity(itemStack: ItemStack) : NMSItemDisplayEntity, NMSDisplayEntity(EntityType.ITEM_DISPLAY) {

    override var itemStack: ItemStack = itemStack
        set(value) {
            entity?.let {
                val itemDisplay = it as ItemDisplay
                itemDisplay.itemStack = CraftItemStack.asNMSCopy(value)
                sendMetaPackets()
                field = value
            }
        }

    override var itemDisplayContext: ItemDisplayContext = ItemDisplayContext.NONE
        set(value) {
            entity?.let {
                val itemDisplay = it as ItemDisplay
                itemDisplay.entityData.set(itemDisplay.DATA_ITEM_DISPLAY_ID(), value.id.toByte())
                sendMetaPackets()
                field = value
            }
        }

    override fun spawn(newLocation: Location) {
        super.spawn(newLocation)

        itemStack = itemStack
        itemDisplayContext = itemDisplayContext
    }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = ItemDisplay(net.minecraft.world.entity.EntityType.ITEM_DISPLAY, level)
}