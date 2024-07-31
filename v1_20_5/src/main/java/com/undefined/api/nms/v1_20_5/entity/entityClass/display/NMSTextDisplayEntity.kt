package com.undefined.api.nms.v1_20_5.entity.entityClass.display

import com.undefined.api.nms.interfaces.display.NMSTextDisplay
import com.undefined.api.nms.v1_20_5.SpigotNMSMappings
import com.undefined.api.nms.v1_20_5.extensions.DATA_BACKGROUND_COLOR_ID
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Display
import net.minecraft.world.entity.Display.TextDisplay
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.entity.EntityType
import java.lang.reflect.Method

class NMSTextDisplayEntity(text: String) : NMSDisplayEntity(EntityType.TEXT_DISPLAY), NMSTextDisplay {

    override var text: String = text
        set(value) {
            entity?.let {
                val textEntity = it as Display.TextDisplay
                textEntity.text = Component.literal(value)
                sendMetaPackets()
                field = value
            }
        }
    override var width: Float = 200F
        set(value) {
            entity?.let {
                val textEntity = it as Display.TextDisplay
                textEntity.width = value
                sendMetaPackets()
                field = value
            }
        }
    override var backGroundColor: Int = 1073741824
        set(value) {
            entity?.let {
                val textEntity = it as Display.TextDisplay
                textEntity.entityData.set(textEntity.DATA_BACKGROUND_COLOR_ID(), value)
                sendMetaPackets()
                field = value
            }
        }
    override var textOpacity: Byte = -1
        set(value) {
            entity?.let {
                val textEntity = it as Display.TextDisplay
                textEntity.textOpacity = value
                sendMetaPackets()
                field = value
            }
        }


    override fun spawn(newLocation: Location) {
        super.spawn(newLocation)
        text = text
    }

    override fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = TextDisplay(entityType, level)
}