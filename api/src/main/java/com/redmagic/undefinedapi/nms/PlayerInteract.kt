package com.redmagic.undefinedapi.nms

class PlayerInteract(val actionType: ClickType, val nmsPlayer: NMSPlayer)

enum class ClickType {

    RIGHT_CLICK,
    LEFT_CLICK

}