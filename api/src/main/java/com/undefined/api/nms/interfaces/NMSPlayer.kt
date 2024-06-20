package com.undefined.api.nms.interfaces

import com.undefined.api.nms.ItemSlot
import com.undefined.api.nms.Pose
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method

/**
 * Represents an NMS player.
 */
interface NMSPlayer: NMSLivingEntity {


    var name: String
    var signature: String
    var texture: String
    val equipped: HashMap<Int, ItemStack?>
    var isCrouching: Boolean
    var isSwimming: Boolean
    var isGliding: Boolean



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
     * Resets the pose of the entity.
     *
     * This method resets the pose of the entity to the default standing position. It can be used to reset any custom
     * poses or animations applied to the entity.
     *
     * Note: This method does not have any parameters or return values.
     */
    fun resetPose()


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
     * Sets the pose of the entity.
     *
     * @param pose the desired pose of the entity
     */
    fun setPose(pose: Pose)

    fun getLivingEntityFlagMethod(): Method

}