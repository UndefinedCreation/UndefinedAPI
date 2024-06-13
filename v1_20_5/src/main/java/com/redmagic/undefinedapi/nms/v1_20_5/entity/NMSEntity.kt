package com.redmagic.undefinedapi.nms.v1_20_5.entity

import com.redmagic.undefinedapi.nms.EntityInteract
import com.redmagic.undefinedapi.nms.interfaces.NMSEntity
import com.redmagic.undefinedapi.nms.v1_20_5.NMSManager
import com.redmagic.undefinedapi.nms.v1_20_5.entity.entityClass.UndefinedEntity
import com.redmagic.undefinedapi.nms.v1_20_5.extensions.sendPacket
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.entity.CraftEntityType
import org.bukkit.craftbukkit.util.CraftChatMessage
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

open class NMSEntity(open val entityType: EntityType): NMSEntity {
    override val viewers: MutableList<Player> = mutableListOf()
    override var location: Location? = null
    var entity: Entity? = null

    override var customName: String? = null
        get() = if (field == null) "" else field
        set(value) {

            if (entity == null) return

            if (value == null){
                //Hide Name

                entity!!.isCustomNameVisible = false

                sendMetaDataPackets()

            }else{
                //Show / Set name

                var name = value

                if (name.length > 256) {
                    name = name.substring(0, 256)
                }

                entity!!.customName = CraftChatMessage.fromStringOrNull(name)

                entity!!.isCustomNameVisible = true

                sendMetaDataPackets()

            }

            field = value
        }

    override fun addViewer(player: Player) {
        viewers.add(player)
    }

    override fun removeViewer(player: Player) {
        viewers.remove(player)
    }


    override fun spawn(newLocation: Location) {
        if (viewers.isEmpty()) {
            return
        }

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

    override fun teleport(newLocation: Location) {
        entity?.let {
            entity!!.setPos(newLocation.x, newLocation.y, newLocation.z)
            entity!!.setRot(newLocation.yaw, newLocation.pitch)

            val packet = ClientboundTeleportEntityPacket(entity!!)

            viewers.sendPacket(packet)
            location = newLocation
        }
    }

    override fun kill() {
        if (entity != null) return

        val entityRemovePacket = ClientboundRemoveEntitiesPacket(entity!!.id)

        viewers.sendPacket(entityRemovePacket)
    }

    override fun getEntityID(): Int = if (entity == null) 0 else entity!!.id

    override fun interact(interact: EntityInteract.() -> Unit) {
        NMSManager.entityInteraction[this] = interact
    }

    open fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = UndefinedEntity(entityType, level)

    private fun sendMetaDataPackets() {
        entity!!.entityData.packDirty()?.let {
            ClientboundSetEntityDataPacket(
                entity!!.id,
                it
            )
        }?.let { viewers.sendPacket(it) }
    }
}