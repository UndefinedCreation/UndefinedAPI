package com.undefined.api.nms.v1_21.extensions

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.block.data.CraftBlockData
import org.bukkit.entity.Player

object BlockExtension {

    fun setBlockProgress(player: Player, block: Block, stage: Int) {

        val packet = ClientboundBlockDestructionPacket(player.entityId, BlockPos(block.x, block.y, block.z), stage)

        player.sendPacket(packet)
    }

    fun getID(blockData: BlockData) : Int = net.minecraft.world.level.block.Block.getId((blockData as CraftBlockData).state)

    fun getBlockDataFromID(id: Int) : BlockData = net.minecraft.world.level.block.Block.stateById(id).createCraftBlockData()

}