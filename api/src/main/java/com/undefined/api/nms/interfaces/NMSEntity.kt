package com.undefined.api.nms.interfaces

import com.undefined.api.nms.EntityInteract
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

interface NMSEntity {
    val viewers: MutableList<Player>
    var customName: String?
    var glowing: Boolean
    var glowingColor: ChatColor
    var isVisible: Boolean
    var collibable: Boolean
    var gravity: Boolean
    val entityType: EntityType
    var onFire: Boolean
    fun addViewer(player: Player)
    fun removeViewer(player: Player)
    var location: Location?
    fun spawn(newLocation: Location)
    fun kill()
    fun teleport(newLocation: Location)
    fun getEntityID(): Int
    fun interact(interact: EntityInteract.() -> Unit)
    fun addPassenger(nmsEntity: NMSEntity)
    fun removePassenger(nmsEntity: NMSEntity)
}