package com.undefined.api.nms.v1_20_6.entity.entityClass.display

import com.undefined.api.nms.interfaces.display.NMSInteractionEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Interaction
import net.minecraft.world.level.Level
import org.bukkit.entity.EntityType

class NMSInteractionEntity: NMSInteractionEntity, NMSDisplayEntity(EntityType.INTERACTION) {

    override var width: Float = 1f
        set(value) {
            entity?.let {
                val interaction = it as Interaction
                interaction.width = value
                sendMetaPackets()
                field = value
            }
        }

    override var height: Float = 1f
        set(value) {
            entity?.let {
                val interaction = it as Interaction
                interaction.height = value
                sendMetaPackets()
                field = value
            }
        }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = Interaction(net.minecraft.world.entity.EntityType.INTERACTION, level)

}