package com.undefined.api.customEvents.block

import com.undefined.api.event.UndefinedEvent
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData


class BlockUpdateEvent(
    val location: Location,
    val block: Block,
    val toData: BlockData,
): UndefinedEvent(true)
