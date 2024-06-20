package com.undefined.api.nms

import com.undefined.api.nms.interfaces.NMSEntity

class EntityInteract(val actionType: ClickType, val nmsEntity: com.undefined.api.nms.interfaces.NMSEntity)

enum class ClickType {

    RIGHT_CLICK,
    LEFT_CLICK

}