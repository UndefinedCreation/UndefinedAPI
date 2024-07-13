package com.undefined.api.nms.v1_20_5.event

import com.undefined.api.customEvents.*
import com.undefined.api.event.event
import com.undefined.api.nms.ClickType
import com.undefined.api.nms.EntityInteract
import com.undefined.api.nms.extensions.removeMetaData
import com.undefined.api.nms.v1_20_5.NMSManager
import com.undefined.api.nms.v1_20_5.extensions.*
import com.undefined.api.scheduler.async
import com.undefined.api.scheduler.sync
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*
import net.minecraft.world.InteractionHand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.CraftParticle
import org.bukkit.craftbukkit.CraftSound
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import java.util.*
import kotlin.collections.ArrayDeque

/**
 * This class represents a packet listener for Minecraft version 1.20.5.
 * It listens for specific events, such as when a player joins or quits the server,
 * and performs certain actions when those events occur.
 *
 * @constructor Creates an instance of PacketListenerManager1_20_5.
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
                            val event = PlayerArmSwingEvent(player, if (this.hand == InteractionHand.MAIN_HAND) EquipmentSlot.HAND else EquipmentSlot.OFF_HAND)
                            Bukkit.getPluginManager().callEvent(event)
                            if (event.isCancelled) return@UndefinedDuplexHandler true
                        }
                        is ServerboundInteractPacket -> handleNPCInteract(this, player)
                        is ServerboundSetCarriedItemPacket -> handleMainHandSwitch(this, player)
                    }

                    return@UndefinedDuplexHandler false
                },{

                    when (this@UndefinedDuplexHandler) {
                        is ClientboundContainerSetSlotPacket -> handleArmorChange(player, this@UndefinedDuplexHandler)
                        is ClientboundSetEntityDataPacket -> handleDataPacket(player, this@UndefinedDuplexHandler)
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
            async {
                val sound = CraftSound.minecraftToBukkit(msg.sound.value())
                val world = Location(viewer.world, it.x, it.y, it.z)
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
     * Handles the fire event for a player based on a given packet.
     * If the player is on fire, it triggers the PlayerIgniteEvent. If the player's fire is extinguished,
     * it triggers the PlayerExtinguishEvent.
     *
     * @param player The player affected by the fire event.
     * @param msg The packet containing the fire event data.
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

            val entity = craftWorld.handle.getEntity(entityID)

            if (value == 0.toByte() && onFire.contains(entity!!.uuid)) {
                Bukkit.getPluginManager()
                    .callEvent(com.undefined.api.customEvents.EntityExtinguishEvent(entity.bukkitEntity))
                onFire.remove(entity.uuid)
            } else if (value == 1.toByte() && !onFire.contains(entity!!.uuid)) {
                Bukkit.getPluginManager()
                    .callEvent(com.undefined.api.customEvents.EntityIgniteEvent(entity.bukkitEntity))
                onFire.add(entity.uuid)
            }
        }
    }

    /**
     * Handles the interaction between an NPC and a player.
     *
     * @param msg The ServerboundInteractPacket representing the interaction message.
     */
    private fun handleNPCInteract(msg: ServerboundInteractPacket, player: Player){

        val actionN = msg.getAction()
        val firstChar = actionN.toString().split("$")[1][0]

        if(isRemapped()){
            if (actionN.toString().contains("InteractionAction")) { return }
        }else{
            if (firstChar != 'e' && firstChar != '1') { return }
        }

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