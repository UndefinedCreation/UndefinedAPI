package com.undefined.api.nms.v1_20_6.entity.livingEntities

import com.undefined.api.nms.interfaces.NMSSlimeEntity
import com.undefined.api.nms.v1_20_6.entity.NMSLivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.monster.Slime
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.entity.EntityType


class NMSSlimeEntity: NMSLivingEntity(EntityType.SLIME), NMSSlimeEntity {
    override var size: Int = 2
        set(value) {
            if (entity == null) return
            field = value

            val slimeEntity = entity!! as Slime

            slimeEntity.setSize(value, true)

            sendMetaPackets()
        }

    override fun spawn(newLocation: Location) {
        super.spawn(newLocation)

        this.size = size
    }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = Slime(net.minecraft.world.entity.EntityType.SLIME, level)
}