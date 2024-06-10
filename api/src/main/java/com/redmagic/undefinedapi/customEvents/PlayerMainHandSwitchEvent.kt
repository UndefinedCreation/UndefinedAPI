package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class PlayerMainHandSwitchEvent(val player: Player, val itemStack: ItemStack?): UndefinedEvent() {
}