package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.nms.interfaces.NMSEntity

class EntityInteract(val actionType: ClickType, val nmsEntity: NMSEntity)

enum class ClickType {

    RIGHT_CLICK,
    LEFT_CLICK

}