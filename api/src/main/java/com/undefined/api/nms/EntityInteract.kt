package com.undefined.api.nms

import com.undefined.api.nms.interfaces.NMSEntity
import org.bukkit.entity.Player

class EntityInteract(val actionType: ClickType, val nmsEntity: NMSEntity, val player: Player)

enum class ClickType {
    RIGHT_CLICK,
    LEFT_CLICK
}