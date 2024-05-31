package com.redmagic.undefinedapi.nms

enum class ItemSlot(val slot: Int){

    MAINHAND(0),
    OFFHAND(40),
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