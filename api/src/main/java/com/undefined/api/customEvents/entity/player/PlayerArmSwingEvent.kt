package com.undefined.api.customEvents.entity.player

import com.undefined.api.event.UndefinedEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot

/**
 * Represents a player arm swing event that is triggered when the player swings their arm.
 *
 * @param player The player who triggered the event.
 * @param interaction The equipment slot that was interacted with during the arm swing.
 */
data class PlayerArmSwingEvent(val player: Player, val hand: EquipmentSlot): UndefinedEvent(true)
