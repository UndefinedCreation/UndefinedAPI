package com.undefined.api.nms.v1_21_3.entity.entityClass.display

import com.undefined.api.nms.Billboard
import com.undefined.api.nms.interfaces.display.NMSDisplayEntity
import com.undefined.api.nms.v1_21_3.entity.NMSEntity
import com.undefined.api.nms.v1_21_3.extensions.*
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Display
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

open class NMSDisplayEntity(entity: EntityType): NMSEntity(entity), NMSDisplayEntity {

    override var scaleX: Float = 0.0F
        set(value) {
            entity?.let {
                val display = it as Display
                display.setScaleX(scaleX)
                updateScale()
                field = value
            }
        }

    override var scaleY: Float = 0.0F
        set(value) {
            entity?.let {
                val display = it as Display
                display.setScaleY(value)
                updateScale()
                field = value
            }
        }

    override var scaleZ: Float = 0.0F
        set(value) {
            entity?.let {
                val display = it as Display
                display.setScaleZ(value)
                updateScale()
                field = value
            }
        }

    override var billboard: Billboard = Billboard.CENTER
        set(value) {
            updateEntityData(15, value.id, EntityDataSerializers.BYTE)
            field = value
        }

    override var transformationInterpolationDuration: Int = 0
        set(value) {
            updateEntityData(9, value, EntityDataSerializers.INT)
            field = value
        }

    override var positionRotationInterpolationDuration: Int = 0
        set(value) {
            updateEntityData(10, value, EntityDataSerializers.INT)
            field = value
        }

    override var interpolationDelay: Int = 0
        set(value) {
            updateEntityData(8, value, EntityDataSerializers.INT)
            field = value
        }


    override fun scale(scale: Float) {
        entity?.let {
            val display = it as Display
            display.setScaleX(scale)
            display.setScaleY(scale)
            display.setScaleZ(scale)
            updateScale()
        }
    }

    override fun offset(x: Float, y: Float, z: Float) {
        entity?.let {
            val display = it as Display
            display.setTranslationX(x)
            display.setTranslationY(y)
            display.setTranslationZ(z)
            updateTranslation()
        }
    }

    override var offsetX: Float = 0F
        set(value) {
            entity?.let {
                val display = it as Display
                display.setTranslationX(value)
                updateTranslation()
                field = value
            }
        }

    override var offsetY: Float = 0F
        set(value) {
            entity?.let {
                val display = it as Display
                display.setTranslationY(value)
                updateTranslation()
                field = value
            }
        }

    override var offsetZ: Float = 0F
        set(value) {
            entity?.let {
                val display = it as Display
                display.setTranslationZ(value)
                updateTranslation()
                field = value
            }
        }

    override var viewRange: Float = 1f
        set(value) {
            entity?.let {
                val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                    SynchedEntityData.DataValue.create(EntityDataAccessor(17, EntityDataSerializers.FLOAT), value)
                )

                viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))
                field = value
            }
        }

    override fun leftRotation(x: Float, y: Float, z: Float) {
        entity?.let {
            val display = it as Display
            val quaternionf = display.setLeftRotation(x, y, z)
            val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(13, EntityDataSerializers.QUATERNION), quaternionf)
            )

            viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))
        }
    }

    override fun rightRotation(x: Float, y: Float, z: Float) {
        entity?.let {
            val display = it as Display
            val quaternionf = display.setRightRotation(x, y, z)
            val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(14, EntityDataSerializers.QUATERNION), quaternionf)
            )

            viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))
        }
    }

    override fun resetOffset() {
        offsetX = 0F
        offsetY = 0F
        offsetZ = 0F
    }

    override fun addViewer(player: Player) {
        super.addViewer(player)
        sendMetaPackets()
    }

    override fun updateScale() {
        entity?.let {
            val display = it as Display
            updateEntityData(12, display.getScale(), EntityDataSerializers.VECTOR3)
        }
    }

    override fun updateTranslation() {
        entity?.let {
            val display = it as Display
            updateEntityData(11, display.getTranslation(), EntityDataSerializers.VECTOR3)
        }
    }

    private fun <T> updateEntityData(accessorNumber: Int, value: T, serializer: EntityDataSerializer<T>) {
        entity?.let {
            val dataList = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(accessorNumber, serializer), value)
            )
            viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList as List<SynchedEntityData.DataValue<*>>?))
        }
    }
}