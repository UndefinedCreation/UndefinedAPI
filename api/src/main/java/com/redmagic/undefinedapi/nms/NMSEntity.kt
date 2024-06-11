package com.redmagic.undefinedapi.nms

import org.bukkit.Location
import org.bukkit.entity.Player

interface NMSEntity {

    val viewers: MutableList<Player>

    fun addViewer(player: Player)
    fun removeViewer(player: Player)

    var location: Location?

    fun spawn(newLocation: Location)

    fun kill()

    fun teleport(newLocation: Location)
}