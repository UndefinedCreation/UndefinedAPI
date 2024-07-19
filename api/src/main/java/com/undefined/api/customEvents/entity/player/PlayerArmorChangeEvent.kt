package com.undefined.api.customEvents.entity.player

import com.undefined.api.event.UndefinedEvent
import com.undefined.api.event.event
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class PlayerArmorChangeEvent(val player: Player, val itemStack: ItemStack, val slot: Int): UndefinedEvent()

class PlayerArmorChangeManager {

    val armorMap: HashMap<String, Int> = hashMapOf(
        Pair("HELMET", 39),
        Pair("CHESTPLATE", 38),
        Pair("LEGGINGS", 37),
        Pair("BOOTS", 36)
    )


    init {

        event<PlayerInteractEvent> {

            if (item == null) return@event

            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (clickedBlock!!.state is InventoryHolder){
                    return@event
                }
            }

            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return@event

            val name = item!!.type.name

            if (!name.contains("_")) return@event

            val last = name.split("_").last

            if (!armorMap.containsKey(last)) return@event

            val slot = armorMap[last]!!

            Bukkit.getPluginManager().callEvent(PlayerArmorChangeEvent(player, item!!, slot))

        }


    }

}