package com.redmagic.undefinedapi.nms.v1_20_5.npc

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.datafixers.util.Pair
import com.redmagic.undefinedapi.nms.v1_20_5.NMSManager
import com.redmagic.undefinedapi.nms.v1_20_5.SpigotNMSMappings
import com.redmagic.undefinedapi.nms.v1_20_5.extensions.getConnection
import com.redmagic.undefinedapi.nms.*
import com.redmagic.undefinedapi.nms.interfaces.NMSPlayer
import com.redmagic.undefinedapi.nms.v1_20_5.entity.NMSLivingEntity
import com.redmagic.undefinedapi.nms.v1_20_5.extensions.sendPacket
import com.redmagic.undefinedapi.scheduler.delay
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ClientInformation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.CommonListenerCookie
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.CraftServer
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method
import java.util.*


/**
 * Represents a player in NMS version 1.20.5.
 *
 * This class provides methods to manipulate and interact with a player,
 * such as moving, teleporting, setting items, changing pose, and more.
 */
class NMSPlayer: NMSPlayer, NMSLivingEntity {
    override val viewers: MutableList<Player> = mutableListOf()
    private var serverPlayer: ServerPlayer? = null
    /**
     * Represents the location of an entity.
     *
     * This variable stores the current location of the entity. It can be set to a new location,
     * and the necessary updates are sent to all the viewers of the entity.
     */
    override var location: Location? = null
    /**
     * The name property represents the name of the player. It is a mutable property of type String.
     * When the value of name is set, it performs some additional actions. If the serverPlayer is null, the method returns.
     * Otherwise, it clones the current location and stores it in tempLocation. Then, it calls the kill() method and passes the tempLocation as a parameter.
     * After that, it spawns the player at the tempLocation by invoking the spawn() method with an empty lambda.
     */
    override var name: String = "Steve"
        set(value) {
            field = value
            serverPlayer ?: return
            val tempLocation = location!!.clone()
            kill()
            spawn(tempLocation)
        }
    override var signature: String = ""
    override var texture: String = ""

    override val equipped: HashMap<Int, ItemStack?> = HashMap()
    /**
     * Represents whether the player is currently crouching or not.
     * When the value of this variable is set, it updates the player's pose accordingly.
     * When the value of this variable is retrieved, it checks if the player's pose is crouching and returns the result.
     */
    override var isCrouching: Boolean = false
        set(value) {
            if (value){
                setPose(Pose.CROUCHING)
            }else{
                setPose(Pose.STANDING)
            }
            field = value
        }
        get() {
            val player = serverPlayer ?: return false
            return player.pose.equals(net.minecraft.world.entity.Pose.CROUCHING)
        }
    /**
     * Gets or sets the swimming status of the player.
     * When setting to true, the player's pose is set to SWIMMING.
     * When setting to false, the player's pose is set to STANDING.
     *
     * @property isSwimming true if the player is swimming, false otherwise.
     */
    override var isSwimming: Boolean = false
        set(value){
            if (value){
                setPose(Pose.SWIMMING)
            }else{
                setPose(Pose.STANDING)
            }
            field = value
        }
        get() {
            val player = serverPlayer ?: return false
            return player.pose.equals(net.minecraft.world.entity.Pose.SWIMMING)
        }
    /**
     * Determines whether the player is gliding or not.
     *
     * @return true if the player is gliding, false otherwise
     */
    override var isGliding: Boolean = false
        set(value){
            if (value){
                setPose(Pose.FALL_FLYING)
            }else{
                setPose(Pose.STANDING)
            }
            field = value
        }
        get() {
            val player = serverPlayer ?: return false
            return player.pose.equals(net.minecraft.world.entity.Pose.FALL_FLYING)
        }

    constructor(name: String, texture: String, sign: String) : super(EntityType.PLAYER) {
        this.name = name
        this.texture = texture
        this.signature = sign
    }

    constructor(name: String, skin: String) : super(EntityType.PLAYER) {
        this.name = name
        val skinString = getSkinTexture(skin)
        texture = skinString[0]
        signature = skinString[1]
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
            mutableListOf(Pair(EquipmentSlot.valueOf(nmsEquipment.name), nmsItemStack))
        )

        viewers.sendPacket(equipmentPacket)

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
     * Uses the main hand of the player.
     *
     * This method starts the player using the item in their main hand.
     * It then invokes the private method "c" in the LivingEntity class
     * with the parameters 1 and true, and then with the parameters 2 and false.
     * After that, it updates the metadata packet for the player.
     */
    override fun useMainHand() {
        val player = serverPlayer ?: return

        if (!equipped.containsKey(ItemSlot.MAINHAND.slot)) return

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


        if (!equipped.containsKey(ItemSlot.OFFHAND.slot)) return

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

        val item = if (equipped.containsKey(ItemSlot.MAINHAND.slot)) equipped[ItemSlot.MAINHAND.slot] else return

        val method = getLivingEntityFlagMethod()
        method.isAccessible = true
        method.invoke(player,1, false)
        updateMetaDataPacket()

        setItem(ItemSlot.MAINHAND, ItemStack(Material.AIR))

        delay(1) { setItem(ItemSlot.MAINHAND, item!!) }
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

        val item = if (equipped.containsKey(ItemSlot.OFFHAND.slot)) equipped[ItemSlot.OFFHAND.slot] else return

        val method = getLivingEntityFlagMethod()
        method.isAccessible = true
        method.invoke(player,1, false)
        updateMetaDataPacket()

        player.stopUsingItem()

        setItem(ItemSlot.OFFHAND, ItemStack(Material.AIR))

        delay(1) { setItem(ItemSlot.OFFHAND, item!!) }
    }

    /**
     * Spawns the player at the specified location and performs the specified action when done.
     *
     * @param newLocation the location at which to spawn the player
     * @param done the action to be performed by the player after spawning
     */
    override fun spawn(newLocation: Location) {

        if (viewers.isEmpty()) {
            return
        }

        this.location = newLocation

        val sPlayer = viewers.random().getConnection()

        val gameProfile = GameProfile(UUID.randomUUID(), name)

        gameProfile.properties.put("textures", Property("textures", texture, signature))

        val server = (Bukkit.getServer() as CraftServer).server
        val serverLevel = sPlayer.player.serverLevel()

        val fakeServerPlayer = ServerPlayer(server, serverLevel, gameProfile, ClientInformation.createDefault())
        fakeServerPlayer.setPos(newLocation.x, newLocation.y, newLocation.z)
        fakeServerPlayer.moveTo(newLocation.x, newLocation.y, newLocation.z, newLocation.yaw, newLocation.pitch)

        val connection = ServerGamePacketListenerImpl(
            server,
            EmptyConnection(),
            fakeServerPlayer,
            CommonListenerCookie.createInitial(gameProfile, false)
        )

        fakeServerPlayer.connection = connection

        serverPlayer = fakeServerPlayer
        entity = serverPlayer

        viewers.forEach{
            sendBasePackets(it)
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
        viewers.sendPacket(packet)
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
        viewers.sendPacket(packet)
    }

    /**
     * Kills the player by performing the death animation and removing them from viewers.
     * After killing the player, their serverPlayer and location properties are set to null.
     */
    override fun kill() {

        viewers.forEach { viewer ->
            removeBasePackets(viewer)
        }

        this.serverPlayer = null
        entity = null
        this.location = null
    }


    /**
     * Removes a player from the list of viewers and removes the base packets for the player.
     *
     * @param player the player to remove as a viewer
     */
    override fun removeViewer(player: Player) {
        super.removeViewer(player)
        removeBasePackets(player)
    }
    /**
     * Adds a player to the list of viewers and sends base packets to the player.
     *
     * @param player the player to add as a viewer
     */
    override fun addViewer(player: Player) {
        super.addViewer(player)
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

        packet?.also { viewers.sendPacket(packet) }
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

        player.sendPacket(addPlayerPacket)
        player.sendPacket(addEntityPacket)

        delay(2) {
            val data = serverPlayer.entityData
            val bitmask: Byte = (0x01 or 0x04 or 0x08 or 0x10 or 0x20 or 0x40 or 127).toByte()
            data.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), bitmask)
            val metaDataPacket = ClientboundSetEntityDataPacket(serverPlayer.id, data.packDirty()!!)

            player.sendPacket(metaDataPacket)
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

        player.sendPacket(infoRemovePacket, entityRemovePacket)

    }

    override fun interact(interact: EntityInteract.() -> Unit) {
        NMSManager.entityInteraction[this] = interact
    }

    /**
     * Resets the pose of the player.
     */
    override fun resetPose() {
        isCrouching = false
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

        val nmsPose = net.minecraft.world.entity.Pose.valueOf(pose.name)

        serverPlayer.pose = nmsPose

        val dataList: MutableList<SynchedEntityData.DataValue<*>> = mutableListOf(
            SynchedEntityData.DataValue.create(EntityDataAccessor(6, EntityDataSerializers.POSE), nmsPose)
        )

        val dataPacket = ClientboundSetEntityDataPacket(serverPlayer.id, dataList)

        viewers.sendPacket(dataPacket)
    }

    /**
     * Returns the method "c" from the LivingEntity class with the specified parameter types.
     *
     * setLivingEntityFlag Map
     *
     * @return the Method object representing the "c" method in the LivingEntity class
     */
    override fun getLivingEntityFlagMethod(): Method = LivingEntity::class.java.getDeclaredMethod(SpigotNMSMappings.LivingEntitySetFlag, Int::class.java, Boolean::class.java)
}