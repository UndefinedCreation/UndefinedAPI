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

enum class SoundSource(name: String, id: Int) {

    MASTER("master",0),
    MUSIC("music",1),
    RECORDS("record",2),
    WEATHER("weather",3),
    BLOCKS("block",4),
    HOSTILE("hostile",5),
    NEUTRAL("neutral",6),
    PLAYERS("players",7),
    AMBIENT("ambient",8),
    VOICE("voice",9)

}