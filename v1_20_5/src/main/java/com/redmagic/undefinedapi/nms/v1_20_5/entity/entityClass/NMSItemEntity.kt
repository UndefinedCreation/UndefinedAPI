package com.redmagic.undefinedapi.nms.v1_20_5.entity.entityClass

import com.redmagic.undefinedapi.nms.interfaces.NMSItemEntity
import com.redmagic.undefinedapi.nms.v1_20_5.entity.NMSEntity
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

class NMSItemEntity(item: ItemStack): NMSEntity(EntityType.ITEM), NMSItemEntity {
    override var itemStack: ItemStack = item
        set(value) {

            entity?.let {

                val itemEntity = entity as ItemEntity

                itemEntity.entityData.set(
                    SynchedEntityData.defineId(
                        ItemEntity::class.java, EntityDataSerializers.ITEM_STACK
                    ), CraftItemStack.asNMSCopy(value))

                sendMetaPackets()
                field = value
            }

        }

    override fun spawn(newLocation: Location) {
        super.spawn(newLocation)
        update()
    }

    override fun update() {
        itemStack = itemStack
    }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = ItemEntity(net.minecraft.world.entity.EntityType.ITEM, level)
}