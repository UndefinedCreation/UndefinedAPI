package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import org.bukkit.entity.Player

/**
 * Represents an event that is triggered when a player is ignited.
 *
 * @property player The player that is ignited.
 * @constructor Creates a new instance of [PlayerIgniteEvent].
 */
class PlayerIgniteEvent(val player: Player): UndefinedEvent()

/**
 * Represents a PlayerExtinguishEvent that is triggered when a player's fire is extinguished.
 *
 * @property player The player affected by the extinguish event.
 */
class PlayerExtinguishEvent(val player: Player): UndefinedEvent()