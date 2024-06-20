package com.undefined.api.customEvents

import com.undefined.api.event.UndefinedEvent
import com.undefined.api.event.event
import com.undefined.api.scheduler.repeatingTask
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDamageAbortEvent
import org.bukkit.event.block.BlockDamageEvent
import kotlin.math.ceil
import kotlin.math.round

class BlockProgressUpdateEvent(val location: Location, val stage: Int): UndefinedEvent()

class BlockProgressManager(){

    private val blockList: HashMap<Location, Int> = hashMapOf()

    init {

        event<BlockDamageEvent> {

            val breakTime = getTimeNeeded(block, player)

            if (breakTime < 2 || player.gameMode != GameMode.SURVIVAL) return@event

            val timeDetween = ceil(breakTime / 10).toInt()

            blockList[block.location] = 0

            Bukkit.getPluginManager().callEvent(BlockProgressUpdateEvent(block.location, 0))

            com.undefined.api.scheduler.repeatingTask(timeDetween) {
                if (!blockList.containsKey(block.location)) {
                    cancel()
                    return@repeatingTask
                }
                var stage = blockList[block.location]!!
                stage++
                if (stage > 9) {
                    blockList.remove(block.location)
                    cancel()
                    return@repeatingTask
                }
                blockList[block.location] = stage
                Bukkit.getPluginManager().callEvent(BlockProgressUpdateEvent(block.location, stage))
            }

        }

        event<BlockBreakEvent> {
            if (blockList.containsKey(block.location)) blockList.remove(block.location)
        }

        event<BlockDamageAbortEvent> {
            if (blockList.containsKey(block.location)) blockList.remove(block.location)
        }

    }

    private fun getTimeNeeded(block: Block, player: Player): Double {
        return round(1.0 / block.getBreakSpeed(player))
    }

}