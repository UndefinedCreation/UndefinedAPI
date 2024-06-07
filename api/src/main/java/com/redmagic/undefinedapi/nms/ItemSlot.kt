package com.redmagic.undefinedapi.nms

enum class ItemSlot(val slot: Int){

    MAINHAND(0),
    OFFHAND(40),
    CHEST(38),
    LEGS(37),
    HEAD(39),
    FEET(36)

}

object ItemSlotObject{
    fun getItemSlotFromSlot(int: Int): ItemSlot?{

        ItemSlot.entries.forEach() {
            if (it.slot == int) {
                return it
            }
        }

        return null
    }
}