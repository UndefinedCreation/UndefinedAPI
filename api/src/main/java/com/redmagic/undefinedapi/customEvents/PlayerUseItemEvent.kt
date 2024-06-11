package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import org.bukkit.entity.Player

class PlayerUseItemEvent(val player: Player, val offhand: Boolean, val starting: Boolean): UndefinedEvent() {
}