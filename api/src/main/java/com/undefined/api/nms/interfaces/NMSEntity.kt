package com.undefined.api.nms.interfaces

import com.undefined.api.nms.EntityInteract
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

interface NMSEntity {
    /**
     * A mutable list of [Player] objects representing viewers.
     *
     * Use this variable to keep track of the viewers currently observing a certain event or content.
     * The [viewers] list can be modified by adding or removing [Player] objects as needed.
     *
     * Note: This list is mutable, meaning that it can be modified after it is instantiated.
     * Please ensure the appropriate synchronization mechanisms are in place if this list is accessed from multiple threads.
     *
     * @see Player
     */
    val viewers: MutableList<Player>

    /**
     * The `customName` variable represents the custom name assigned to an entity.
     * It is a nullable `String` type, allowing for the possibility of no custom name being assigned.
     *
     * This variable is a member of the `NMSEntity` interface.
     *
     * Example usage:
     * ```
     * val entity: NMSEntity = SomeEntity()
     * entity.customName = "Custom Name"
     * ```
     *
     * @see NMSEntity
     */
    var customName: String?

    /**
     * Represents the state of an object's glow effect.
     */
    var glowing: Boolean

    /**
     * Represents the color of a glowing effect in Minecraft.
     *
     * The ChatColor enum classifies the different colors that can be used for a glowing effect
     * in Minecraft. The colors range from a standard set of predefined colors.
     *
     * @property [colorCode] The Minecraft color code associated with this glowing color.
     */
    var glowingColor: ChatColor

    /**
     * A boolean variable indicating the visibility status.
     */
    var isVisible: Boolean

    /**
     * Boolean flag indicating if the entity is collidable.
     */
    var collibable: Boolean

    /**
     * Boolean flag indicating whether the entity is affected by gravity.
     * If set to `true`, the entity will fall downwards due to gravity.
     * If set to `false`, the entity will remain in the air and not be affected by gravity.
     *
     * @see NMSEntity
     */
    var gravity: Boolean

    /**
     * Adds a viewer to the player's list of viewers.
     *
     * @param player The player to add the viewer to.
     */
    fun addViewer(player: Player)

    /**
     * Removes a viewer from the player's list of viewers.
     *
     * @param player the player from which to remove the viewer
     */
    fun removeViewer(player: Player)

    /**
     * Represents a location.
     *
     * @property location The location object, nullable.
     */
    var location: Location?

    /**
     * Spawns an entity at the given location.
     *
     * @param newLocation The location where the entity should be spawned.
     */
    fun spawn(newLocation: Location)

    /**
     * Kills the process or entity associated with this method.
     */
    fun kill()

    /**
     * Teleports the user to a new location.
     *
     * @param newLocation the new location to teleport to
     */
    fun teleport(newLocation: Location)

    /**
     * Returns the ID of the entity.
     *
     * @return The ID of the entity as an integer.
     */
    fun getEntityID(): Int

    /**
     * Executes interaction with an entity.
     *
     * @param interact A lambda function representing the interaction action to be performed on the entity.
     *        The lambda function receives an instance of [EntityInteract], which contains the action type and the entity.
     *        Use the [actionType] property to determine the type of click (RIGHT_CLICK or LEFT_CLICK),
     *        and use the [nmsEntity] property to access the entity itself.
     */
    fun interact(interact: EntityInteract.() -> Unit)
    /**
     * Adds a passenger to the entity.
     *
     * @param nmsEntity The entity to add as a passenger.
     */
    fun addPassenger(nmsEntity: NMSEntity)

    /**
     * Removes a passenger from the given NMSEntity.
     *
     * @param nmsEntity The NMSEntity from which the passenger will be removed.
     */
    fun removePassenger(nmsEntity: NMSEntity)
}

