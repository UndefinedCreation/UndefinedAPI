package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import org.bukkit.entity.Player

class PlayerIgniteEvent(val player: Player): UndefinedEvent()

class PlayerExtinguishEvent(val player: Player): UndefinedEvent()