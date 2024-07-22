package com.undefined.api.nms.v1_21.entity.entityClass.display

import com.undefined.api.nms.interfaces.display.NMSBlockDisplayEntity
import net.minecraft.world.entity.Display.BlockDisplay
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.block.data.CraftBlockData
import org.bukkit.entity.EntityType

class NMSBlockDisplayEntity(block: BlockData): NMSDisplayEntity(EntityType.BLOCK_DISPLAY), NMSBlockDisplayEntity {
    override var blockData: BlockData = block
        set(value) {
            entity?.let {

                val b = it as BlockDisplay
                b.blockState = (value as CraftBlockData).state
                sendMetaPackets()
                field = value

            }
        }

    override fun spawn(newLocation: Location) {
        super.spawn(newLocation)
        blockData = blockData
    }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = BlockDisplay(entityType, level)
}