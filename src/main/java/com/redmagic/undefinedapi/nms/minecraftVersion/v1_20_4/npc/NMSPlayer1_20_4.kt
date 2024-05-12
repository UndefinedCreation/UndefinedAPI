package com.redmagic.undefinedapi.nms.minecraftVersion.v1_20_4.npc

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.datafixers.util.Pair
import com.redmagic.undefinedapi.nms.extension.getConnection
import com.redmagic.undefinedapi.nms.extension.getSkinTexture
import com.redmagic.undefinedapi.nms.extension.toDeltaValue
import com.redmagic.undefinedapi.nms.extension.toRotationValue
import com.redmagic.undefinedapi.nms.interfaces.entity.ItemSlot
import com.redmagic.undefinedapi.nms.interfaces.entity.ItemSlotObject
import com.redmagic.undefinedapi.nms.interfaces.entity.NMSPlayer
import com.redmagic.undefinedapi.scheduler.delay
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ClientInformation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.CommonListenerCookie
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Pose
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_20_R3.CraftServer
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method
import java.util.*


/**
 * Represents a player in NMS version 1.20.4.
 *
 * This class provides methods to manipulate and interact with a player,
 * such as moving, teleporting, setting items, changing pose, and more.
 */
class NMSPlayer1_20_4(name: String, skin: String): NMSPlayer {
    override val viewers: MutableList<Player> = mutableListOf()
    override var serverPlayer: ServerPlayer? = null
    override var location: Location? = null
    override var name: String = "Steve"
    override var signature: String = ""
    override var texture: String = ""

    override val equipped: HashMap<Int, ItemStack?> = HashMap()
    override var isCrouching: Boolean = false
    override var isSwimming: Boolean = false
    override var isGliding: Boolean = false
    override var onFire: Boolean = false


    init {
        this.name = name
        val skinString = getSkinTexture(skin)
        texture = skinString[0]
        signature = skinString[1]
    }

    /**
     * Moves the player to a new location.
     *
     * If distance is bigger than it will cancel. Instead, you should use [NMSPlayer.teleport]
     *
     * @param newLocation the new location to move to
     */
    override fun moveTo(newLocation: Location) {
        val currentLocation = location ?: return
        val player = serverPlayer ?: return

        if (currentLocation.distance(newLocation) > 8.0)
            return

        val deltaX = toDeltaValue(currentLocation.x, newLocation.x)
        val deltaY =  toDeltaValue(currentLocation.y, newLocation.y)
        val deltaZ = toDeltaValue(currentLocation.z, newLocation.z)
        val isOnGround = newLocation.clone().subtract(0.0,0.1,0.0).block.type.isSolid

        val headRotationYaw = toRotationValue(newLocation.yaw)
        val headRotationPitch = toRotationValue(newLocation.pitch)

        val packet = ClientboundMoveEntityPacket.PosRot(
            player.bukkitEntity.entityId,
            deltaX,
            deltaY,
            deltaZ,
            headRotationYaw,
            headRotationPitch,
            isOnGround
        )

        viewers.forEach {
            it.getConnection().send(packet)
        }

        location = newLocation

    }

    /**
     * Moves the player to a new location by teleporting them.
     *
     * @param newLocation the new location to move to
     */
    override fun teleport(newLocation: Location): Unit = with(newLocation) {
        serverPlayer?.let { player ->
            player.setPos(Vec3(x, y, z))
            player.setRot(yaw, pitch)

            viewers.forEach { viewer ->
                viewer.getConnection().send(ClientboundTeleportEntityPacket(player))
            }

            location = this
        }
    }

    /**
     * Moves the player to a new location if the distance between the current location and the new location is less than or equal to 8.0,
     * otherwise teleports the player to the new location.
     *
     * @param newLocation the new location to move or teleport to
     */
    override fun moveOrTeleport(newLocation: Location) {
        serverPlayer?.let {
            location?.let {
                if (it.distance(newLocation) > 8.0) {
                    teleport(newLocation)
                } else {
                    moveTo(newLocation)
                }
            }
        }
    }

    /**
     * Sets the display name of the player.
     *
     * This method sets the display name of the player to the specified string. It also performs additional actions such as updating the player's name and
     * respawning the player at their current location.
     *
     * @param string the new display name for the player
     */
    override fun setDisplayName(string: String) {
        serverPlayer ?: return
        name = string
        val tempLocation = location!!.clone()
        kill()
        spawn(tempLocation){}
    }

    /**
     * Sets the skin of the object based on the provided string.
     *
     * @param string the string representing the desired skin
     */
    override fun setSkin(string: String) {
        val stringArray = getSkinTexture(string)
        val tempLocation = location!!.clone()
        texture = stringArray[0]
        signature = stringArray[1]
        kill()
        spawn(tempLocation)
    }

    /**
     * Sets the skin of an entity with the given texture and signature.
     *
     * @param texture the texture of the skin
     * @param sign the signature associated with the skin
     */
    override fun setSkin(texture: String, sign: String) {
        this.texture = texture
        this.signature = sign
        val tempLocation = location!!.clone()
        kill()
        spawn(tempLocation)
    }

    /**
     * Clears all items in the item slots of the player.
     */
    override fun clearItems() = ItemSlot.entries.forEach{ setItem(it, ItemStack(Material.AIR)) }
    /**
     * Sets the item in the specified slot for the player.
     *
     * @param slot The slot index where the item will be set.
     * @param itemStack The ItemStack to be set in the slot.
     */
    override fun setItem(slot: Int, itemStack: ItemStack) {
        val player = serverPlayer ?: return
        val nmsEquipment = ItemSlotObject.getItemSlotFromSlot(slot) ?: return

        val nmsItemStack = CraftItemStack.asNMSCopy(itemStack)

        player.inventory.setItem(slot, nmsItemStack)

        val equipmentPacket = ClientboundSetEquipmentPacket(
            player.bukkitEntity.entityId,
            mutableListOf(Pair(nmsEquipment.equipmentSlot, nmsItemStack))
        )

        viewers.forEach{
            it.getConnection().send(equipmentPacket)
        }

        updateMetaDataPacket()
        
        equipped[slot] = itemStack
    }
    /**
     * Sets the item in the specified slot for the player.
     *
     * @param itemSlot The slot where the item will be set.
     * @param itemStack The ItemStack to be set in the slot.
     */
    override fun setItem(itemSlot: ItemSlot, itemStack: ItemStack) = setItem(itemSlot.slot, itemStack)
    /**
     * Crouches the player.
     *
     * This method checks if the player is already crouching. If so, the method returns without performing any actions.
     * Otherwise, it sets the isCrouching flag to true, sets the player's pose to CROUCHING, and sends the updated pose to all viewers of the player.
     */
    override fun crouch() {
        if (isCrouching) return
        isCrouching = !isCrouching
        isSwimming = false
        isGliding = false
        setPose(Pose.CROUCHING)
    }
    /**
     * Uncrouches the player.
     *
     * If the player is already standing, this method returns without performing any actions.
     * Otherwise, it changes the player's pose to standing and updates the metadata packets for all viewers.
     */
    override fun uncrouch() {
        if (!isCrouching) return
        isCrouching = !isCrouching
        setPose(Pose.STANDING)
    }

    /**
     * Sets the swimming state for the player.
     *
     * If the player is already swimming, the method returns immediately.
     * Otherwise, it sets the player's swimming state to true and updates their pose to swimming.
     */
    override fun swim() {
        if (isSwimming) return
        isSwimming = !isSwimming
        isCrouching = false
        isGliding = false
        setPose(Pose.SWIMMING)
    }

    /**
     * Unswims the player.
     *
     * This method checks if the player is currently swimming. If not, it returns without performing any actions.
     * Otherwise, it sets the player's swimming state to false and changes the player's pose to standing.
     */
    override fun unswim() {
        if (!isSwimming) return
        isSwimming = !isSwimming
        setPose(Pose.STANDING)
    }

    /**
     * Glides the player.
     */
    override fun glide() {
        if (isGliding) return
        isGliding = !isGliding
        isSwimming = false
        isCrouching = false
        setPose(Pose.FALL_FLYING)
    }

    /**
     * Disables gliding for the player.
     */
    override fun unglide() {
        if (!isGliding) return
        isGliding = !isGliding
        setPose(Pose.STANDING)
    }

    /**
     * Sets the player on fire and sends the necessary packets to all viewers.
     */
    override fun ignite() {

        if (onFire) return

        onFire = !onFire

        val serverPlayer = serverPlayer ?: return

        serverPlayer.remainingFireTicks = 2000000

        val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
            SynchedEntityData.DataValue.create(EntityDataAccessor(0, EntityDataSerializers.BYTE), 0x01)
        )

        val dataPacket = ClientboundSetEntityDataPacket(serverPlayer.id, dataList)

        viewers.forEach{
            it.getConnection().send(dataPacket)
        }
    }

    /**
     * Extinguishes the player if they are on fire.
     *
     * If the player is on fire, this method extinguishes the fire and sends update packets to all viewers to reflect the change.
     */
    override fun extinguish() {
        if (!onFire) return

        onFire = !onFire

        val serverPlayer = serverPlayer ?: return

        serverPlayer.remainingFireTicks = 0

        val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
            SynchedEntityData.DataValue.create(EntityDataAccessor(0, EntityDataSerializers.BYTE), 0)
        )

        val dataPacket = ClientboundSetEntityDataPacket(serverPlayer.id, dataList)

        viewers.forEach{
            it.getConnection().send(dataPacket)
        }
    }

    /**
     * Uses the main hand of the player.
     *
     * This method starts the player using the item in their main hand.
     * It then invokes the private method "c" in the LivingEntity class
     * with the parameters 1 and true, and then with the parameters 2 and false.
     * After that, it updates the metadata packet for the player.
     */
    override fun useMainHand() {
        val player = serverPlayer ?: return

        if (!equipped.containsKey(ItemSlot.MAIN_HAND.slot)) return

        val method = getLivingEntityFlagMethod()
        method.isAccessible = true

        method.invoke(player,1, true)
        method.invoke(player, 2, false)

        updateMetaDataPacket()
    }

    /**
     * Uses the off-hand of the player.
     *
     * This method starts the player using the item in their off-hand.
     * It then invokes the private method "c" in the LivingEntity class
     * with the parameters 1 and true, and then with the parameters 2 and true.
     * After that, it updates the metadata packet for the player.
     */
    override fun useOffHand() {
        val player = serverPlayer ?: return

        if (!equipped.containsKey(ItemSlot.OFF_HAND.slot)) return

        val method = getLivingEntityFlagMethod()
        method.isAccessible = true

        method.invoke(player,1, true)
        method.invoke(player, 2, true)

        updateMetaDataPacket()

    }

    /**
     * Stops the player from using the item in their main hand.
     * This method starts by checking if the player is currently holding an item in their main hand.
     * If not, the method returns without performing any actions.
     * Otherwise, it proceeds with the following steps:
     * 1. Retrieves the current server player instance. If it is null, the method returns.
     * 2. Checks if the main hand slot is equipped with an item. If not, the method returns.
     * 3. Invokes the private method "c" in the LivingEntity class with the parameters 1 and false to stop using the main hand item.
     * 4. Updates the metadata packet to reflect any changes in the player's entity data.
     * 5. Sets the main hand slot to an empty ItemStack.
     * 6. Delays for 1 tick and sets the main hand slot back to the original item.
     */
    override fun stopUsingMainHandItem() {
        val player = serverPlayer ?: return

        val item = if (equipped.containsKey(ItemSlot.MAIN_HAND.slot)) equipped[ItemSlot.MAIN_HAND.slot] else return

        val method = getLivingEntityFlagMethod()
        method.isAccessible = true
        method.invoke(player,1, false)
        updateMetaDataPacket()

        setItem(ItemSlot.MAIN_HAND, ItemStack(Material.AIR))

        delay(1) { setItem(ItemSlot.MAIN_HAND, item!!) }
    }

    /**
     * Stops the player from using the item in their off-hand slot.
     * If the player is not currently holding an item in their off-hand, the method does nothing.
     *
     * The method starts by checking if the player is online. If not, it returns without performing any actions.
     *
     * Next, it retrieves the item in the off-hand slot, if it exists. If the off-hand slot is empty, the method returns without performing any actions.
     *
     * The method then uses reflection to access and invoke the private method "c" in the LivingEntity class.
     * It passes the parameters 1 and false to the method to stop the player from using their off-hand item.
     *
     * After that, it updates the metadata packet for the player to reflect any changes in their entity data.
     *
     * Finally, it sets the off-hand slot to an empty ItemStack (air) and schedules a delay of 1 tick.
     * During the delay, the off-hand slot is set back to the original item to complete the stop using off-hand item process.
     */
    override fun stopUsingOffHandItem() {
        val player = serverPlayer ?: return

        val item = if (equipped.containsKey(ItemSlot.OFF_HAND.slot)) equipped[ItemSlot.OFF_HAND.slot] else return

        val method = getLivingEntityFlagMethod()
        method.isAccessible = true
        method.invoke(player,1, false)
        updateMetaDataPacket()

        setItem(ItemSlot.OFF_HAND, ItemStack(Material.AIR))

        delay(1) { setItem(ItemSlot.OFF_HAND, item!!) }
    }

    /**
     * Spawns the player at the specified location and performs the specified action when done.
     *
     * @param location the location at which to spawn the player
     * @param done the action to be performed by the player after spawning
     */
    override fun spawn(location: Location, done: NMSPlayer.() -> Unit) {

        if (viewers.isEmpty()) {
            return
        }

        this.location = location

        val sPlayer = viewers.random().getConnection()

        val gameProfile = GameProfile(UUID.randomUUID(), name)

        gameProfile.properties.put("textures", Property("textures", texture, signature))

        val server = (Bukkit.getServer() as CraftServer).server
        val serverLevel = sPlayer.player.serverLevel()

        val fakeServerPlayer = ServerPlayer(server, serverLevel, gameProfile, ClientInformation.createDefault())
        fakeServerPlayer.setPos(location.x, location.y, location.z)
        fakeServerPlayer.setRot(location.yaw, location.pitch)

        val connection = ServerGamePacketListenerImpl(
            server,
            EmptyConnection1_20_4(),
            fakeServerPlayer,
            CommonListenerCookie.createInitial(gameProfile)
        )

        fakeServerPlayer.connection = connection

        serverPlayer = fakeServerPlayer

        viewers.forEach{
            sendBasePackets(it)
        }

        done.invoke(this)

    }

    /**
     * Perform damage animation for the player.
     */
    override fun damageAnimation() {
        val player = serverPlayer ?: return
        val packet = ClientboundHurtAnimationPacket(
            player.bukkitEntity.entityId,
            0f
        )
        viewers.forEach{
            it.getConnection().send(packet)
        }
    }

    /**
     * Moves the player's main hand.
     */
    override fun moveMainHand() {
        val player = serverPlayer ?: return
        val packet = ClientboundAnimatePacket(
            player,
            0
        )
        viewers.forEach{
            it.getConnection().send(packet)
        }
    }

    /**
     * Moves the player's offhand.
     */
    override fun moveOffHand() {
        val player = serverPlayer ?: return
        val packet = ClientboundAnimatePacket(
            player,
            3
        )
        viewers.forEach{
            it.getConnection().send(packet)
        }
    }

    /**
     * Kills the player.
     *
     * This method sends animation packets to all viewers, removes base packets, and sets the player's serverPlayer and location to null.
     */
    override fun kill() {
        val serverPlayer = serverPlayer ?: return

        val animatepacket1 = ClientboundEntityEventPacket(serverPlayer, 3)
        val animatePacket2 = ClientboundEntityEventPacket(serverPlayer, 60)

        viewers.forEach { viewer ->
            viewer.getConnection().let { connection ->
                connection.send(animatepacket1)
                connection.send(animatePacket2)
                removeBasePackets(viewer)
            }
        }

        this.serverPlayer = null
        this.location = null
    }

    /**
     * Removes a player from the list of viewers and removes the base packets for the player.
     *
     * @param player the player to remove as a viewer
     */
    override fun removeViewer(player: Player) {
        viewers.remove(player)
        removeBasePackets(player)
    }
    /**
     * Adds a player to the list of viewers and sends base packets to the player.
     *
     * @param player the player to add as a viewer
     */
    override fun addViewer(player: Player) {
        viewers.add(player)
        sendBasePackets(player)
    }

    /**
     * Updates the metadata packet for the player.
     * This method checks if the serverPlayer property is null. If it is null, the method returns without performing any actions.
     * Otherwise, it creates a metadata packet using the non-default values from the player's entity data.
     * If the packet is not null, it sends the packet to all viewers of the player.
     *
     */
    override fun updateMetaDataPacket() {
        val player = serverPlayer ?: return
        val packet = player.entityData.nonDefaultValues?.let {
            ClientboundSetEntityDataPacket(player.bukkitEntity.entityId, it)
        }

        packet?.let { validPacket ->
            viewers.forEach{ viewer ->
                viewer.getConnection().send(validPacket)
            }
        }
    }

    /**
     * Sends base packets to the specified player.
     *
     * @param player the player to send the packets to
     */
    override fun sendBasePackets(player: Player) {
        val serverPlayer = serverPlayer ?: return

        val addPlayerPacket = ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer)
        val addEntityPacket = ClientboundAddEntityPacket(serverPlayer)

        player.getConnection().let { connection ->
            connection.send(addPlayerPacket)
            connection.send(addEntityPacket)

            delay(2) {
                val data = serverPlayer.entityData
                val bitmask: Byte = (0x01 or 0x04 or 0x08 or 0x10 or 0x20 or 0x40 or 127).toByte()
                data.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), bitmask)
                val metaDataPacket = ClientboundSetEntityDataPacket(serverPlayer.id, data.packDirty()!!)

                connection.send(metaDataPacket)
            }
        }
    }

    /**
     * Removes the base packets for a given player.
     *
     * @param player the player to remove the base packets from
     */
    override fun removeBasePackets(player: Player) {

        val serverPlayer = serverPlayer ?: return

        val infoRemovePacket = ClientboundPlayerInfoRemovePacket(listOf(serverPlayer.uuid))
        val entityRemovePacket = ClientboundRemoveEntitiesPacket(serverPlayer.id)

        player.getConnection().let { connection ->
            connection.send(infoRemovePacket)
            connection.send(entityRemovePacket)
        }

    }

    /**
     * Returns whether the player is alive.
     *
     * @return true if the player is alive, false otherwise.
     */
    override fun isAlive(): Boolean = serverPlayer != null
    /**
     * Sets the pose of the player.
     *
     * @param pose the new pose to set for the player
     */
    override fun setPose(pose: Pose) {
        val serverPlayer = serverPlayer ?: return
        serverPlayer.pose = pose

        val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
            SynchedEntityData.DataValue.create(EntityDataAccessor(6, EntityDataSerializers.POSE), pose)
        )

        val dataPacket = ClientboundSetEntityDataPacket(serverPlayer.id, dataList)

        viewers.forEach{
            it.getConnection().send(dataPacket)
        }
    }

    /**
     * Returns the method "c" from the LivingEntity class with the specified parameter types.
     *
     * @return the Method object representing the "c" method in the LivingEntity class
     */
    override fun getLivingEntityFlagMethod(): Method = LivingEntity::class.java.getDeclaredMethod("c", Int::class.java, Boolean::class.java)
}