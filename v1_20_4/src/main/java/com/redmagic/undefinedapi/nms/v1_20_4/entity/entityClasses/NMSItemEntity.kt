package com.redmagic.undefinedapi.nms.v1_20_4.entity.entityClasses

import com.redmagic.undefinedapi.nms.interfaces.NMSItemEntity
import com.redmagic.undefinedapi.nms.v1_20_4.entity.NMSEntity
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

class NMSItemEntity(item: ItemStack): NMSEntity(EntityType.DROPPED_ITEM), NMSItemEntity {

    private val DATA_ITEM: EntityDataAccessor<net.minecraft.world.item.ItemStack> = SynchedEntityData.defineId(
        ItemEntity::class.java, EntityDataSerializers.ITEM_STACK
    )

    override var itemStack: ItemStack = item
        set(value) {

            entity?.let {
                it.entityData.set(DATA_ITEM, CraftItemStack.asNMSCopy(value))

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