package com.undefined.api.customEvents.entity.player

import com.undefined.api.event.UndefinedEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class PlayerMainHandSwitchEvent(val player: Player, val itemStack: ItemStack?): UndefinedEvent() {
}