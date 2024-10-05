package com.undefined.api.customEvents.entity.player

import com.undefined.api.event.UndefinedEvent
import org.bukkit.entity.Player

class PlayerUseItemEvent(val player: Player, val offhand: Boolean, val starting: Boolean): UndefinedEvent()