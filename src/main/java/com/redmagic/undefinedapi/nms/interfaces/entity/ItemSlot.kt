package com.redmagic.undefinedapi.nms.interfaces.entity

import net.minecraft.world.entity.EquipmentSlot
import org.bukkit.entity.Item

enum class ItemSlot(val slot: Int, val equipmentSlot: EquipmentSlot){

    MAIN_HAND(0, EquipmentSlot.MAINHAND),
    OFF_HAND(40, EquipmentSlot.OFFHAND),
    CHESTPLATE(38, EquipmentSlot.CHEST),
    LEGGINGS(37, EquipmentSlot.LEGS),
    HELMET(39, EquipmentSlot.HEAD),
    BOOTS(36, EquipmentSlot.FEET)

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