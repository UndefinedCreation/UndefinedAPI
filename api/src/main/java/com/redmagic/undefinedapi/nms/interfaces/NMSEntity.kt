package com.redmagic.undefinedapi.nms.interfaces

import com.redmagic.undefinedapi.nms.EntityInteract
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

interface NMSEntity {

    val viewers: MutableList<Player>

    var customName: String?

    var glowing: Boolean
    var glowingColor: ChatColor

    var isVisible: Boolean

    fun addViewer(player: Player)

    fun removeViewer(player: Player)

    var location: Location?

    fun spawn(newLocation: Location)

    fun kill()

    fun teleport(newLocation: Location)

    fun getEntityID(): Int

    fun interact(interact: EntityInteract.() -> Unit)
}