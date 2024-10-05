package com.undefined.api.nms.v1_20_6.entity.entityClass

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_20_6.SpigotNMSMappings
import com.undefined.api.nms.v1_20_6.entity.NMSEntity
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

class NMSItemEntity(item: ItemStack): NMSEntity(EntityType.ITEM), com.undefined.api.nms.interfaces.NMSItemEntity {
    private val ITEM_DATA: EntityDataAccessor<net.minecraft.world.item.ItemStack>?
        get() {
            entity?.let {
                return it.getPrivateField(SpigotNMSMappings.ItemEntityAccessor)
            }
            return null
        }
    override var itemStack: ItemStack = item
        set(value) {
            entity?.let {
                val itemEntity = entity as ItemEntity
                itemEntity.entityData.set(ITEM_DATA!!, CraftItemStack.asNMSCopy(value))
                sendMetaPackets()
                field = value
            }
        }

    override fun spawn(newLocation: Location) {
        super.spawn(newLocation)
        sendMetaPackets()
    }

    override fun update() {
        itemStack = itemStack
    }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = ItemEntity(level, 0.0,0.0,0.0, CraftItemStack.asNMSCopy(itemStack))
}