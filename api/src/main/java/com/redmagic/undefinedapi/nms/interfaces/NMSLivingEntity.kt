package com.redmagic.undefinedapi.nms.interfaces

import org.bukkit.Location

interface NMSLivingEntity: NMSEntity {


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
     * Triggers a death animation for the entity.
     *
     * This method plays the animation of the entity dying. It does not directly apply any damage or modify the entity's health.
     * The animation can be used to provide visual feedback to the player or create special effects.
     *
     * @receiver The entity to perform the death animation on.
     */
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

    fun setHeadRotation(yaw: Float)

    /**
     * Checks if the entity is alive.
     *
     * @return true if the entity is alive, false otherwise.
     */
    fun isAlive(): Boolean

}