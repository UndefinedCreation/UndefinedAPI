package com.redmagic.undefinedapi.nms.interfaces.entity

import org.bukkit.entity.Player

interface NMSEntity {

    val viewers: MutableList<Player>

    fun addViewer(player: Player)
    fun removeViewer(player: Player)

}