package com.undefined.api.nms.interfaces.display

import org.bukkit.inventory.ItemStack

interface NMSItemDisplayEntity: NMSDisplayEntity {

    var itemStack: ItemStack

    var itemDisplayContext: ItemDisplayContext

}

enum class ItemDisplayContext(val id: Int) {
    NONE(0),
    THIRD_PERSON_LEFT_HAND(1),
    THIRD_PERSON_RIGHT_HAND(2),
    FIRST_PERSON_LEFT_HAND(3),
    FIRST_PERSON_RIGHT_HAND(4),
    HEAD(5),
    GUI(6),
    GROUND(7),
    FIXED(8)
}