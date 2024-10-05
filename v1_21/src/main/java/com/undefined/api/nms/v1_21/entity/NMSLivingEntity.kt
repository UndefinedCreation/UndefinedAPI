package com.undefined.api.nms.v1_21.entity

import com.undefined.api.nms.interfaces.NMSLivingEntity
import com.undefined.api.nms.toDeltaValue
import com.undefined.api.nms.toRotationValue
import com.undefined.api.nms.v1_21.extensions.sendPacket
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType

open class NMSLivingEntity(override var entityType: EntityType): NMSLivingEntity, NMSEntity(entityType) {

    override fun moveTo(newLocation: Location) {
        val currentLocation = location ?: return
        val entity = entity ?: return

        if (currentLocation.distance(newLocation) > 8.0) return

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
                if (it.distance(newLocation) > 8.0) teleport(newLocation) else moveTo(newLocation)
            }
        }
    }

    override fun setHeadRotation(yaw: Float) {
        entity?.let {
            val headRot = ClientboundRotateHeadPacket(it, toRotationValue(yaw))
            viewers.sendPacket(headRot)
        }
    }

    override fun setRotation(pitch: Float, yaw: Float) {
        entity?.let {
            viewers.sendPacket(ClientboundMoveEntityPacket.Rot(it.id, toRotationValue(yaw), toRotationValue(pitch), true))
        }
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
        val packet = ClientboundHurtAnimationPacket(entity.id, 0f)
        viewers.sendPacket(packet)
    }
}