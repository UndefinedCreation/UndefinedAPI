package com.redmagic.undefinedapi.nms.v1_20_4.entity

import com.redmagic.undefinedapi.nms.EntityInteract
import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import com.redmagic.undefinedapi.nms.interfaces.NMSEntity
import com.redmagic.undefinedapi.nms.v1_20_4.NMSManager
import com.redmagic.undefinedapi.nms.v1_20_4.entity.entityClasses.UndefinedEntity
import com.redmagic.undefinedapi.nms.v1_20_4.extensions.sendPacket
import net.minecraft.ChatFormatting
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.scores.PlayerTeam
import net.minecraft.world.scores.Scoreboard
import net.minecraft.world.scores.Team
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntityType
import org.bukkit.craftbukkit.v1_20_R3.util.CraftChatMessage
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import kotlin.random.Random

open class NMSEntity(open val entityType: EntityType): NMSEntity {
    override val viewers: MutableList<Player> = mutableListOf()
    override var location: Location? = null
    var entity: Entity? = null

    private val scoreboard = Scoreboard()
    private val team = scoreboard.addPlayerTeam("glow")

    override var glowingColor: ChatColor = ChatColor.WHITE
        set(value) {

            if (entity == null) return

            field = value

            val format = ChatFormatting.valueOf(value.name)
            team.color = format

            viewers.sendPacket(
                ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true),
                ClientboundSetPlayerTeamPacket.createPlayerPacket(team, entity!!.uuid.toString(), ClientboundSetPlayerTeamPacket.Action.ADD)
            )

        }

    override var glowing: Boolean = false
        set(value) {

            if (entity == null) return

            field = value

            entity!!.setGlowingTag(value)

            sendMetaPackets()
        }


    override var collibable: Boolean = false
        set(value) {

            if (entity == null) return

            field = value

            team.collisionRule = if (value) Team.CollisionRule.ALWAYS else Team.CollisionRule.NEVER

            viewers.sendPacket(
                ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true),
            )
        }
    override var isVisible: Boolean = true
        set(value) {

            if (entity == null) return

            entity!!.isInvisible = !value

            sendMetaPackets()

            field = value
        }

    override var customName: String? = null
        get() = if (field == null) "" else field
        set(value) {

            if (entity == null) return

            if (value == null){
                //Hide Name

                entity!!.isCustomNameVisible = false

                sendMetaPackets()

            }else{
                //Show / Set name

                var name = value

                if (name.length > 256) {
                    name = name.substring(0, 256)
                }

                entity!!.customName = CraftChatMessage.fromStringOrNull(name)

                entity!!.isCustomNameVisible = true

                sendMetaPackets()
            }

            field = value
        }

    override fun addViewer(player: Player) {
        viewers.add(player)
        scoreboard.addPlayerToTeam(player.name, team)
    }

    override fun removeViewer(player: Player) {
        viewers.remove(player)
        scoreboard.removePlayerFromTeam(player.name)
    }


    override fun spawn(newLocation: Location) {
        if (viewers.isEmpty()) {
            return
        }

        val nmsEntityType = CraftEntityType.bukkitToMinecraft(entityType)

        val craftWorld = newLocation.world as CraftWorld

        val entity = getUndefinedEntityClass(nmsEntityType, craftWorld.handle)

        entity.setPos(newLocation.x, newLocation.y, newLocation.z)
        entity.setRot(newLocation.yaw, newLocation.pitch)

        scoreboard.addPlayerToTeam(entity.uuid.toString(), team)

        val packet = entity.addEntityPacket

        team.collisionRule = Team.CollisionRule.NEVER
        team.setSeeFriendlyInvisibles(false)

        viewers.sendPacket(
            packet,
            ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true),
            ClientboundSetPlayerTeamPacket.createPlayerPacket(team, entity.uuid.toString(), ClientboundSetPlayerTeamPacket.Action.ADD)
        )
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
        if (entity == null) return

        val entityRemovePacket = ClientboundRemoveEntitiesPacket(entity!!.id)

        viewers.sendPacket(entityRemovePacket)
    }

    override fun getEntityID(): Int = if (entity == null) 0 else entity!!.id

    override fun interact(interact: EntityInteract.() -> Unit) {
        NMSManager.entityInteraction[this] = interact
    }

    open fun getUndefinedEntityClass(entityType: net.minecraft.world.entity.EntityType<*>, level: Level): Entity = UndefinedEntity(entityType, level)

    fun sendMetaPackets() {
        if (entity == null) return
        entity!!.entityData.nonDefaultValues?.let {
            ClientboundSetEntityDataPacket(
                entity!!.id,
                it
            )
        }?.let { viewers.sendPacket(it) }

    }
}