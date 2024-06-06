package com.redmagic.undefinedapi.nms.v1_20_5.extensions

import com.mojang.brigadier.context.ContextChain.Stage
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import org.bukkit.block.Block
import org.bukkit.entity.Player

object BlockExtension {

    fun setBlockProgress(player: Player, block: Block, stage: Int) {

        val packet = ClientboundBlockDestructionPacket(player.entityId, BlockPos(block.x, block.y, block.z), stage)

        player.sendPacket(packet)
    }

}