package com.undefined.api.nms.v1_20_4.entity.entityClasses.display

import com.undefined.api.nms.interfaces.display.NMSBlockDisplayEntity
import net.minecraft.world.entity.Display.BlockDisplay
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.v1_20_R3.block.data.CraftBlockData
import org.bukkit.entity.EntityType

class NMSBlockDisplayEntity(block: BlockData) : NMSDisplayEntity(EntityType.BLOCK_DISPLAY), NMSBlockDisplayEntity {

    override var blockData: BlockData = block
        set(value) {
            entity?.let {
                val blockDisplay = it as BlockDisplay
                blockDisplay.blockState = (value as CraftBlockData).state
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