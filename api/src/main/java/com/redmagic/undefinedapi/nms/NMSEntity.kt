package com.redmagic.undefinedapi.nms

import org.bukkit.entity.Player

interface NMSEntity {

    val viewers: MutableList<Player>

    fun addViewer(player: Player)
    fun removeViewer(player: Player)

}