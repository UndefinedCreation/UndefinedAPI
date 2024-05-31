package com.redmagic.undefinedapi.nms

import org.bukkit.Location
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
     * @param newLocation The destination location to move the entity to.
     */
    fun moveTo(newLocation: Location)
    /**
     * Moves or teleports the entity to the specified location.
     *
     * @param newLocation the destination location to move or teleport the entity to
     */
    fun moveOrTeleport(newLocation: Location)


    /**
     * Sets the skin of the player character.
     *
     * This method is used to set the skin of the player character to a specified texture.
     *
     * @param string The texture value representing the skin of the player character.
     */
    fun setSkin(string: String)

    /**
     * Sets the skin of the NMSPlayer using the provided texture and sign.
     *
     * @param texture The texture value of the skin.
     * @param sign The sign value of the skin.
     */
    fun setSkin(texture: String, sign: String)

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
     * Stops using the currently equipped item in the main hand slot.
     *
     * This method is used to stop using the currently equipped item by switching to the empty main hand.
     * The item will no longer be considered "in use" by the player.
     *
     * Note: This method does not return any value.
     */
    fun stopUsingMainHandItem()

    /**
     * Stops using the currently equipped item in the off-hand slot.
     *
     * This method is used to stop using the currently equipped item by switching to the empty off-hand.
     * The item will no longer be considered "in use" by the player.
     * Note: This method does not return any value.
     */
    fun stopUsingOffHandItem()

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
    fun spawn(location: Location, done : NMSPlayer.() -> Unit = {})
    /**
     * Kills the entity.
     */
    fun kill()
    fun deathAnimation()

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