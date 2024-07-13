package com.undefined.api.customEvents

import com.undefined.api.event.UndefinedEvent
import org.bukkit.Sound
import org.bukkit.entity.Player

class SoundStopEvent(
    val player: Player,
    val source: SoundSource,
    val sound: Sound
) : UndefinedEvent()