package com.undefined.api.nms.v1_20_4.event

import com.undefined.api.customEvents.*
import com.undefined.api.event.event
import com.undefined.api.nms.ClickType
import com.undefined.api.nms.EntityInteract
import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.extensions.getPrivateFieldFromSuper
import com.undefined.api.nms.extensions.removeMetaData
import com.undefined.api.nms.v1_20_4.NMSManager
import com.undefined.api.nms.v1_20_4.extensions.*
import com.undefined.api.scheduler.async
import com.undefined.api.scheduler.delay
import com.undefined.api.scheduler.repeatingTask
import com.undefined.api.scheduler.sync
import net.kyori.adventure.bossbar.BossBar
import net.minecraft.core.particles.BlockParticleOption
import net.minecraft.core.particles.DustColorTransitionOptions
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.particles.ItemParticleOption
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.SculkChargeParticleOptions
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.block.SoundType
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Particle.DustTransition
import org.bukkit.World
import org.bukkit.block.BlockState
import org.bukkit.craftbukkit.v1_20_R3.CraftParticle
import org.bukkit.craftbukkit.v1_20_R3.CraftParticle.CraftParticleRegistry
import org.bukkit.craftbukkit.v1_20_R3.CraftSound
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.HashMap
import kotlin.collections.MutableList
import kotlin.collections.filter
import kotlin.collections.forEach
import kotlin.collections.hashMapOf
import kotlin.collections.mutableListOf

/**
 * This class represents a packet listener for Minecraft version 1.20.4.
 * It listens for specific events, such as when a player joins or quits the server,
 * and performs certain actions when those events occur.
 *
 * @constructor Creates an instance of PacketListenerManager1_20_4.
 */
class PacketListenerManager {

    private val armorSlots: HashMap<Int, Int> = hashMapOf(Pair(4, 39), Pair(5, 38), Pair(6, 37), Pair(7, 36))

    private val onFire: MutableList<UUID> = mutableListOf()

    private val que = ArrayDeque<Packet<*>>(6)


    private val lMap: HashMap<UUID, UUID> = hashMapOf()

    init {

        event<PlayerJoinEvent> {
            if (player.fireTicks > 0) {
                onFire.add(player.uniqueId)
            }

            lMap[player.uniqueId] = UUID.randomUUID()

            val fakeConnection = player.getConnection().getConnection()

            val channel = fakeConnection.channel
            val pipeline = channel.pipeline()
            pipeline.addBefore("packet_handler", lMap[player.uniqueId]!!.toString(), UndefinedDuplexHandler(
                {

                    when (this) {
                        is ServerboundSwingPacket -> {
                            val event = PlayerArmSwingEvent(player, if (this.hand.equals(InteractionHand.MAIN_HAND)) EquipmentSlot.HAND else EquipmentSlot.OFF_HAND)
                            Bukkit.getPluginManager().callEvent(event)
                            if (event.isCancelled) return@UndefinedDuplexHandler true
                        }
                        is ServerboundInteractPacket -> handleNPCInteract(this, player)
                        is ServerboundSetCarriedItemPacket -> handleMainHandSwitch(this, player)
                    }

                return@UndefinedDuplexHandler false
            },{
                    when (this@UndefinedDuplexHandler) {
                        is ClientboundSetEntityDataPacket -> handleDataPacket(player, this@UndefinedDuplexHandler)
                        is ClientboundContainerSetSlotPacket -> handleArmorChange(player, this@UndefinedDuplexHandler)
                        is ClientboundLevelParticlesPacket -> handleParticle(this, player.world, player)
                        is ClientboundSoundPacket -> handleSound(this, player)
                        is ClientboundSoundEntityPacket -> handleEntitySound(this, player)
                        is ClientboundStopSoundPacket -> handleSoundStop(this, player)
                    }
                return@UndefinedDuplexHandler false
            }
            ))

        }

        event<PlayerQuitEvent> {

            player.removeMetaData("onFire")

            val connection = player.getConnection().getConnection()
            val channel = connection.channel
            channel.eventLoop().submit(){
                channel.pipeline().remove(lMap[player.uniqueId]!!.toString())
            }

            lMap.remove(player.uniqueId)
        }

    }

    private fun handleSoundStop(msg: ClientboundStopSoundPacket, viewer: Player) {
        async {
            msg.source?.let { source ->
                msg.name?.let {
                    SoundStopEvent(
                        viewer,
                        com.undefined.api.customEvents.SoundSource.valueOf(source.name),
                        CraftSound.minecraftToBukkit(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(it))
                    ).call()
                }
            }
        }
    }

    private fun handleEntitySound(msg: ClientboundSoundEntityPacket, viewer: Player) {
        val cWorld = viewer.world as CraftWorld
        cWorld.handle.getEntity(msg.id)?.let {
            val sound = CraftSound.minecraftToBukkit(msg.sound.value())
            val world = Location(viewer.world, it.x, it.y, it.z)
            val volume = msg.volume
            val pitch = msg.pitch
            val seed = msg.seed
            val source = com.undefined.api.customEvents.SoundSource.valueOf(msg.source.name)
            SoundEvent(
                viewer,
                sound,
                volume,
                pitch,
                world,
                seed,
                source
            ).call()
        }
    }

    private fun handleSound(msg: ClientboundSoundPacket, viewer: Player) {
        async {
            val sound = CraftSound.minecraftToBukkit(msg.sound.value())
            val world = Location(viewer.world, msg.x, msg.y, msg.z)
            val volume = msg.volume
            val pitch = msg.pitch
            val seed = msg.seed
            val source = com.undefined.api.customEvents.SoundSource.valueOf(msg.source.name)
            sync {
                SoundEvent(
                    viewer,
                    sound,
                    volume,
                    pitch,
                    world,
                    seed,
                    source
                ).call()
            }
        }
    }

    private fun handleParticle(msg: ClientboundLevelParticlesPacket, world: World, viewer: Player) {
        async {
            val location = Location(world, msg.x, msg.y, msg.z)
            val particle = CraftParticle.minecraftToBukkit(msg.particle.type)
            val option = msg.particle.getBukkitOptions()
            val count = msg.count
            val maxSpeed = msg.maxSpeed
            val dirX = msg.xDist
            val dirY = msg.yDist
            val dirZ = msg.zDist

            sync {
                ParticleEvent(
                    location,
                    particle,
                    option,
                    count,
                    maxSpeed,
                    dirX,
                    dirY,
                    dirZ,
                    viewer
                ).call()
            }
        }
    }



    private fun handleMainHandSwitch(msg: ServerboundSetCarriedItemPacket, player: Player) {

        val slot = msg.getEntityID()

        val item = player.inventory.getItem(slot)

        com.undefined.api.scheduler.sync {
            Bukkit.getPluginManager().callEvent(PlayerMainHandSwitchEvent(player, item))
        }

    }

    private fun handleArmorChange(player: Player, msg: ClientboundContainerSetSlotPacket) {
        val sPlayer = (player as CraftPlayer).handle
        val windowID = sPlayer.containerMenu.containerId

        val contairID = msg.getContainerID()

        if (windowID != contairID) return

        val slot = msg.getContainerSlot()

        if (!armorSlots.containsKey(slot)) return

        val bukkitSlot = armorSlots[slot]!!

        val itemStack = msg.getItemStack()

        com.undefined.api.scheduler.sync {
            Bukkit.getPluginManager().callEvent(PlayerArmorChangeEvent(player, itemStack.bukkitStack, bukkitSlot))
        }

    }

    /**
     * Handles the interaction between a player and fire.
     *
     * @param player The player involved in the interaction.
     * @param msg The ClientboundSetEntityDataPacket containing the data of the interaction.
     */
    private fun handleDataPacket(player: Player, msg: ClientboundSetEntityDataPacket) {

        val id = msg.getEntityID()

        val list = msg.getSynchedEntityDataList()

        list.filter { it.value is Byte }.forEach {

            when (it.id) {
                0 -> handleFire(msg, it.value as Byte, player.world as CraftWorld)
                8 -> if (player.entityId == id) handleUsingItem(player, (it.value as Byte).toInt())
            }
        }
        return
    }

    private fun handleUsingItem(player: Player, value: Int) {
        com.undefined.api.scheduler.sync {
            Bukkit.getPluginManager().callEvent(
                PlayerUseItemEvent(
                    player,
                    value >= 2,
                    value == 1 || value == 3
                )
            )
        }
    }

    private fun handleFire(msg: ClientboundSetEntityDataPacket, value: Byte, craftWorld: CraftWorld){

        if (!checkQue(msg)) return

        val entityID = msg.getEntityID()

        com.undefined.api.scheduler.sync {

            craftWorld.handle.getEntity(entityID)?.let {

                if (!net.minecraft.world.entity.LivingEntity::class.java.isAssignableFrom(it::class.java)) return@sync

                if (value == 0.toByte() && onFire.contains(it.uuid)) {
                    Bukkit.getPluginManager()
                        .callEvent(com.undefined.api.customEvents.EntityExtinguishEvent(it.bukkitEntity))
                    onFire.remove(it.uuid)
                } else if (value == 1.toByte() && !onFire.contains(it.uuid)) {
                    Bukkit.getPluginManager()
                        .callEvent(com.undefined.api.customEvents.EntityIgniteEvent(it.bukkitEntity))
                    onFire.add(it.uuid)
                }
            }


        }
    }

    /**
     * Handles the interaction between a player and an NPC.
     *
     * @param msg The ServerboundInteractPacket containing the interaction data.
     */
    private fun handleNPCInteract(msg: ServerboundInteractPacket, player: Player){

        val firstChar = msg.getActionFirstChar()

        if (firstChar == 'e') { return }

        val attacking = msg.isAttacking()

        if (!attacking){
            if (!msg.isMainHand()) return
        }

        val action = if(attacking) ClickType.LEFT_CLICK else ClickType.RIGHT_CLICK



        NMSManager.entityInteraction.entries.forEach {
            if (it.key.getEntityID() == msg.getEntityID()) {
                it.value.invoke(EntityInteract(action, it.key, player))
            }
        }
    }


    private fun checkQue(packet: Packet<*>): Boolean {
        if (que.size == 6)  que.removeLast()

        for (p in que) {
            if (p == packet) return false
        }

        que.addFirst(packet)
        return true
    }

}

