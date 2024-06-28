package com.undefined.api.nms.v1_20_5.entity.entityClass.display

import com.undefined.api.nms.Billboard
import com.undefined.api.nms.interfaces.NMSDisplayEntity
import com.undefined.api.nms.v1_20_5.entity.NMSEntity
import com.undefined.api.nms.v1_20_5.extensions.*
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Display
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

open class NMSDisplayEntity(entity: EntityType): NMSEntity(entity), NMSDisplayEntity {
    override var scaleX: Float = 0.0F
        set(value) {
            entity?.let {

                val d = it as Display
                d.setScaleX(scaleX)

                updateScale()

                field = value
            }
        }
    override var scaleY: Float = 0.0F
        set(value) {

            entity?.let {

                val e = it as Display
                e.setScaleY(value)
                updateScale()
                field = value

            }

        }
    override var scaleZ: Float = 0.0F
        set(value) {
            entity?.let {

                val e = it as Display
                e.setScaleZ(value)
                updateScale()
                field = value

            }
        }
    override var billboard: Billboard = Billboard.CENTER
        set(value) {
            entity?.let {

                val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                    SynchedEntityData.DataValue.create(EntityDataAccessor(15, EntityDataSerializers.BYTE), value.id)
                )

                viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))

                field = value

            }
        }

    override fun scale(scale: Float) {
        entity?.let {

            val d = it as Display
            d.setScaleX(scale)
            d.setScaleY(scale)
            d.setScaleZ(scale)
            updateScale()
        }
    }

    override fun offset(x: Float, y: Float, z: Float) {
        entity?.let {
            val d = it as Display
            d.setTranslationX(x)
            d.setTranslationY(y)
            d.setTranslationZ(z)
            updateTranslation()
        }
    }

    override var offsetX: Float = 0F
        set(value) {
            entity?.let {

                val e = it as Display
                e.setTranslationX(value)
                updateTranslation()
                field = value

            }
        }
    override var offsetY: Float = 0F
        set(value) {
            entity?.let {

                val e = it as Display
                e.setTranslationY(value)
                updateTranslation()
                field = value

            }
        }
    override var offsetZ: Float = 0F
        set(value) {
            entity?.let {

                val e = it as Display
                e.setTranslationZ(value)
                updateTranslation()
                field = value

            }
        }


    override fun leftRotation(x: Float, y: Float, z: Float) {
        entity?.let {
            val e = it as Display
            val q = e.setLeftRotation(x, y, z)

            val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(13, EntityDataSerializers.QUATERNION), q)
            )

            viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))
        }
    }

    override fun rightRotation(x: Float, y: Float, z: Float) {
        entity?.let {
            val e = it as Display
            val q = e.setRightRotation(x, y, z)

            val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(14, EntityDataSerializers.QUATERNION), q)
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

            val d = it as Display

            val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(12, EntityDataSerializers.VECTOR3), d.getScale())
            )

            viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))

        }
    }

    override fun updateTranslation() {
        entity?.let {

            val d = it as Display

            val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                SynchedEntityData.DataValue.create(EntityDataAccessor(11, EntityDataSerializers.VECTOR3), d.getTranslation())
            )

            viewers.sendPacket(ClientboundSetEntityDataPacket(it.id, dataList))

        }
    }
}