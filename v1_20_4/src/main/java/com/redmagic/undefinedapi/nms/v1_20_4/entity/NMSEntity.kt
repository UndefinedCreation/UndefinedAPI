package com.redmagic.undefinedapi.nms.v1_20_4.entity

import com.redmagic.undefinedapi.nms.NMSLivingEntity
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import com.redmagic.undefinedapi.nms.toDeltaValue
import com.redmagic.undefinedapi.nms.toRotationValue
import com.redmagic.undefinedapi.nms.v1_20_4.extensions.sendPacket
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import net.minecraft.world.entity.Entity
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntityType
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class NMSEntity(val entityType: EntityType): NMSLivingEntity {

    private var entity: Entity? = null
    override val viewers: MutableList<Player> = mutableListOf()

    override var onFire: Boolean = false

    override var location: Location? = null

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

    override fun teleport(newLocation: Location) {
        entity?.let {
            entity!!.setPos(newLocation.x, newLocation.y, newLocation.z)
            entity!!.setRot(newLocation.yaw, newLocation.pitch)

            val packet = ClientboundTeleportEntityPacket(entity!!)

            viewers.sendPacket(packet)
            location = newLocation
        }
    }

    override fun isAlive(): Boolean = entity != null


    override fun spawn(newLocation: Location) {
        val nmsEntityType = CraftEntityType.bukkitToMinecraft(entityType)

        val craftWorld = newLocation.world as CraftWorld

        val entity = UndefinedEntity(nmsEntityType, craftWorld.handle)

        entity.setPos(newLocation.x, newLocation.y, newLocation.z)
        entity.setRot(newLocation.yaw, newLocation.pitch)

        val packet = entity.addEntityPacket

        viewers.sendPacket(packet)
        this.entity = entity
        this.location = newLocation

    }

    override fun kill() {
        TODO("Not yet implemented")
    }

    override fun deathAnimation() {
        TODO("Not yet implemented")
    }

    override fun damageAnimation() {
        TODO("Not yet implemented")
    }

    override fun addViewer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun removeViewer(player: Player) {
        TODO("Not yet implemented")
    }
}