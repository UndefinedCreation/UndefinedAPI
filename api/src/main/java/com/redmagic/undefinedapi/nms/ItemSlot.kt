package com.redmagic.undefinedapi.nms

enum class ItemSlot(val slot: Int){

    MAIN_HAND(0),
    OFF_HAND(40),
    CHESTPLATE(38),
    LEGGINGS(37),
    HELMET(39),
    BOOTS(36)

}

object ItemSlotObject{
    fun getItemSlotFromSlot(int: Int): ItemSlot?{

        ItemSlot.entries.forEach(){
            if (it.slot == int) {
                return it
            }
        }

        return null
    }
}