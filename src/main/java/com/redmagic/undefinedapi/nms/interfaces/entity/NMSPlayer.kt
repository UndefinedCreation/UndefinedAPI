package com.redmagic.undefinedapi.nms.interfaces.entity

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Pose
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method

/**
 * Represents an NMS player.
 */
interface NMSPlayer: NMSEntity {

    /**
     * Represents the server-side player in the game.
     *
     * @property serverPlayer The NMS player instance associated with the server-side player.
     */
    var serverPlayer: ServerPlayer?

    var location: Location?
    var name: String
    var signature: String
    var texture: String
    val equipped: HashMap<Int, ItemStack?>
    var isCrouching: Boolean
    var isSwimming: Boolean
    var isGliding: Boolean
    var onFire: Boolean


    /**
     * Moves the entity to the specified location.
     *
     * @param location The destination location to move the entity to.
     */
    fun moveTo(location: Location)
    /**
     * Teleports the entity to the specified location.
     *
     * @param location The destination location to move the entity to.
     */
    fun teleport(location: Location)
    /**
     * Moves or teleports the entity to the specified location.
     *
     * @param location the destination location to move or teleport the entity to
     */
    fun moveOrTeleport(location: Location)

    /**
     * Sets the name of the entity.
     *
     * @param string the new name of the entity
     */
    fun setDisplayName(string: String)
    /**
     * Sets the skin of the player to the specified name.
     *
     * @param name The name of the skin.
     */
    fun setSkin(name: String)
    /**
     * Sets the skin of the player entity with the specified signature and texture.
     *
     * @param signature The signature of the player's skin
     * @param texture The texture of the player's skin
     */
    fun setSkin(signature: String, texture: String)


    /**
     * Clears all items from the inventory of the NMSPlayer.
     */
    fun clearItems()
    /**
     * Sets the item in the specified slot of the inventory.
     *
     * @param slot The slot index where the item should be set.
     * @param itemStack The item stack to be set in the slot.
     */
    fun setItem(slot: Int, itemStack: ItemStack)
    /**
     *
     */
    fun setItem(itemSlot: ItemSlot, itemStack: ItemStack)

    /**
     * Makes the player character crouch.
     *
     * This method is called to make the player character crouch. It adjusts the player's position
     * to simulate the crouching behavior. When the player character is crouching, their hitbox becomes
     * smaller, allowing them to fit into tighter spaces.
     *
     * Note: This method does not return any value.
     */
    fun crouch()
    /**
     * Uncrouches the NMSPlayer.
     *
     * This method changes the crouching state of the NMSPlayer,
     * causing them to stand up if they were crouching.
     *
     * The NMSPlayer must be alive for this method to have any effect.
     */
    fun uncrouch()

    /**
     * Makes the player start swimming.
     *
     * This method is used to make the player start swimming.
     * After calling this method, the player will enter the swimming state and will be able to move through water,
     * swim upwards, and dive downwards.
     * The player will also be able to perform swimming animations.
     */
    fun swim()
    /**
     * Unswims the player.
     *
     * This method is used to make the player stop swimming.
     * It changes the player's state from swimming to standing.
     * After calling this method, the player will be able to walk on land normally.
     */
    fun unswim()

    /**
     * Makes the entity start gliding.
     */
    fun glide()
    /**
     * Disables gliding for the player.
     */
    fun unglide()

    /**
     * Sets the player on fire.
     *
     * This method sets the player on fire, causing them to take damage over time.
     * The player will continue to be on fire until the fire is extinguished using the `extinguish` method.
     *
     * This method does not return any value.
     */
    fun ignite()
    /**
     * This method is used to extinguish the player if they are on fire.
     * It removes the fire effect from the player.
     */
    fun extinguish()

    /**
     * Sets the player's main hand for item interactions.
     */
    fun useMainHand()
    /**
     * Sets the player to use their off-hand for item interactions.
     * Any item interactions performed by the player after calling this method will use the off-hand slot.
     *
     * This method does not have a return value.
     */
    fun useOffHand()

    /**
     * Stops using the currently equipped item.
     *
     * This method is used to stop using the currently equipped item by switching to the empty hand.
     * The item will no longer be considered "in use" by the player.
     *
     * This method does not return any value.
     */
    fun stopUsingItem()

    /**
     * Moves the player's main hand for item interactions.
     *
     * This method sets the player's main hand for item interactions. Any item interactions performed by the player
     * after calling this method will use the main hand slot.
     *
     * This method does not have a return value.
     */
    fun moveMainHand()
    /**
     * Moves the off-hand item to the main hand slot.
     * This method is used to switch the player's item from the off-hand to the main hand.
     * It does not return any value.
     */
    fun moveOffHand()

    /**
     * Spawns a server player entity at the specified location.
     *
     * @param location the location where the server player should be spawned
     * @param done a lambda function that will be executed after the server player is spawned
     */
    fun spawn(location: Location, done : ServerPlayer.() -> Unit)
    /**
     * Kills the entity.
     */
    fun kill()

    /**
     * Triggers a damage animation for the entity.
     *
     * This method plays the animation of the entity being damaged. It does not directly apply any damage or modify the entity's health.
     * The animation can be used to provide visual feedback to the player or create special effects.
     *
     * @receiver The entity to perform the damage animation on.
     */
    fun damageAnimation()

    /**
     * Updates the metadata packet for the entity.
     */
    fun updateMetaDataPacket()

    /**
     * Sends base packets to the specified player.
     *
     * @param player the player to send base packets to
     */
    fun sendBasePackets(player: Player)

    /**
     * Removes the base packets for the given player.
     *
     * @param player The player for whom to remove the base packets.
     */
    fun removeBasePackets(player: Player)

    /**
     * Checks if the entity is alive.
     *
     * @return true if the entity is alive, false otherwise.
     */
    fun isAlive(): Boolean

    /**
     * Sets the pose of the entity.
     *
     * @param pose the desired pose of the entity
     */
    fun setPose(pose: Pose)

    fun getLivingEntityFlagMethod(): Method

}