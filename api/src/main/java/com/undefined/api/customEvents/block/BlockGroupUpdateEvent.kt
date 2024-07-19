package com.undefined.api.customEvents.block

import com.undefined.api.event.UndefinedEvent
import org.bukkit.Location
import org.bukkit.block.data.BlockData

class BlockGroupUpdateEvent(
    val blocks: List<Pair<Location, BlockData>>
) : UndefinedEvent(true)