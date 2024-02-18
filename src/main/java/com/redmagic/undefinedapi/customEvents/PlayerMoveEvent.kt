package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import com.redmagic.undefinedapi.scheduler.repeatingTask
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID


/**
 * Manages player movement and triggers a PlayerMoveEvent when a player moves.
 */
class PlayerMoveManager{
    init {
        val map = HashMap<UUID, Location>()

        repeatingTask(5) {
            Bukkit.getOnlinePlayers().forEach{
                if (!map.containsKey(it.uniqueId)){
                    map[it.uniqueId] = it.location
                }else{
                    val fromLocation = map[it.uniqueId]
                    if (fromLocation!! != it.location){

                        val event = PlayerMoveEvent(it, fromLocation)

                        Bukkit.getPluginManager().callEvent(event)
                        if (event.isCancelled){
                            it.teleport(fromLocation)
                        }else {
                            map[it.uniqueId] = it.location
                        }
                    }
                }
            }
        }
    }
}

/**
 * Represents an event that is triggered when a player moves.
 *
 * @property player The player who moved.
 * @property fromLocation The location the player moved from.
 */
class PlayerMoveEvent(val player: Player, val fromLocation: Location): UndefinedEvent()