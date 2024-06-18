package com.redmagic.undefinedapi.nms.v1_21.entity

import com.redmagic.undefinedapi.nms.interfaces.NMSLivingEntity
import com.redmagic.undefinedapi.nms.toDeltaValue
import com.redmagic.undefinedapi.nms.toRotationValue
import com.redmagic.undefinedapi.nms.v1_21.extensions.sendPacket
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import org.bukkit.Location
import org.bukkit.entity.EntityType

open class NMSLivingEntity(override var entityType: EntityType): NMSLivingEntity, NMSEntity(entityType) {

    override var onFire: Boolean = false
        set(value){



            val entity = entity ?: return
            field = value
            if (value){

                entity.remainingFireTicks = 2000000

                val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                    SynchedEntityData.DataValue.create(EntityDataAccessor(0, EntityDataSerializers.BYTE), 0x01)
                )

                val dataPacket = ClientboundSetEntityDataPacket(entity.id, dataList)

                viewers.sendPacket(dataPacket)
            }else{
                entity.remainingFireTicks = 0

                val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
                    SynchedEntityData.DataValue.create(EntityDataAccessor(0, EntityDataSerializers.BYTE), 0)
                )

                val dataPacket = ClientboundSetEntityDataPacket(entity.id, dataList)

                viewers.sendPacket(dataPacket)
            }

        }

    override fun moveTo(newLocation: Location) {
        val currentLocation = location ?: return
        val entity = entity ?: return

        if (currentLocation.distance(newLocation) > 8.0)
            return

        val deltaX = toDeltaValue(currentLocation.x, newLocation.x)
        val deltaY =  toDeltaValue(currentLocation.y, newLocation.y)
        val deltaZ = toDeltaValue(currentLocation.z, newLocation.z)
        val isOnGround = newLocation.clone().subtract(0.0,0.1,0.0).block.type.isSolid

        val headRotationYaw = toRotationValue(newLocation.yaw)
        val headRotationPitch = toRotationValue(newLocation.pitch)

        val movement = ClientboundMoveEntityPacket.PosRot(
            entity.id,
            deltaX,
            deltaY,
            deltaZ,
            headRotationYaw,
            headRotationPitch,
            isOnGround
        )

        val headRot = ClientboundRotateHeadPacket(
            entity,
            headRotationYaw
        )

        viewers.sendPacket(headRot)
        viewers.sendPacket(movement)

        location = newLocation
    }

    override fun moveOrTeleport(newLocation: Location) {
        entity?.let {
            location?.let {
                if (it.distance(newLocation) > 8.0) {
                    teleport(newLocation)
                } else {
                    moveTo(newLocation)
                }
            }
        }
    }

    override fun setHeadRotation(yaw: Float) {
        if (entity == null) return
        val headRot = ClientboundRotateHeadPacket(
            entity,
            toRotationValue(yaw)
        )

        viewers.sendPacket(headRot)
    }

    override fun isAlive(): Boolean = entity != null

    override fun deathAnimation() {
        val serverPlayer = entity ?: return

        val animatepacket1 = ClientboundEntityEventPacket(serverPlayer, 3)
        val animatePacket2 = ClientboundEntityEventPacket(serverPlayer, 60)

        viewers.sendPacket(animatepacket1, animatePacket2)
    }

    override fun damageAnimation() {
        val entity = entity ?: return
        val packet = ClientboundHurtAnimationPacket(
            entity.id,
            0f
        )
        viewers.sendPacket(packet)
    }
}