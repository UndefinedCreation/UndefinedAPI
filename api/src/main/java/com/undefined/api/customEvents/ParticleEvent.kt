package com.undefined.api.customEvents

import com.undefined.api.event.UndefinedEvent
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

class ParticleEvent(
    val location: Location,
    val particle: Particle,
    val options: Any?,
    val count: Int,
    val maxSpeed: Float,
    val xDist: Float,
    val yDist: Float,
    val zDist: Float,
    player: Player
): UndefinedEvent() {
}