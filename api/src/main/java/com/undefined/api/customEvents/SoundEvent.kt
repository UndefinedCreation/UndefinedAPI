package com.undefined.api.customEvents

import com.undefined.api.event.UndefinedEvent
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player

class SoundEvent(
    val player: Player,
    val sound: Sound,
    val volume: Float,
    val pitch: Float,
    val location: Location,
    val seed: Long,
    val soundSource: SoundSource
) : UndefinedEvent()

enum class SoundSource(name: String) {

    MASTER("master"),
    MUSIC("music"),
    RECORDS("record"),
    WEATHER("weather"),
    BLOCKS("block"),
    HOSTILE("hostile"),
    NEUTRAL("neutral"),
    PLAYERS("players"),
    AMBIENT("ambient"),
    VOICE("voice")

}