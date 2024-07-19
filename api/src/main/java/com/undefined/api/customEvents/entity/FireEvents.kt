package com.undefined.api.customEvents.entity

import com.undefined.api.event.UndefinedEvent
import org.bukkit.entity.Entity

/**
 * Represents an event that is triggered when a player is ignited.
 *
 * @property player The player that is ignited.
 * @constructor Creates a new instance of [EntityIgniteEvent].
 */
class EntityIgniteEvent(val entity: Entity): UndefinedEvent()

/**
 * Represents a PlayerExtinguishEvent that is triggered when a player's fire is extinguished.
 *
 * @property player The player affected by the extinguish event.
 */
class EntityExtinguishEvent(val entity: Entity): UndefinedEvent()