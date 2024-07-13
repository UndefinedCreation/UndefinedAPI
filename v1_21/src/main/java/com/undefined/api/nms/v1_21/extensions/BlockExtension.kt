package com.undefined.api.nms.v1_21.extensions

import it.unimi.dsi.fastutil.shorts.ShortArraySet
import it.unimi.dsi.fastutil.shorts.ShortSet
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.Location
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

    fun setBlockArray(state: Array<BlockData>, posList: List<Location>, list: List<Player>) {

        val changes: HashMap<SectionPos, ChunkSectionChanges> = HashMap()

        for (index in state.indices) {
            val bPos = posList[index]

            val pos = BlockPos(bPos.blockX, bPos.blockY, bPos.blockZ)

            val sectionPos = SectionPos.of(pos)

            val sectionChanges = changes.getOrPut(sectionPos) { ChunkSectionChanges(ShortArraySet(), mutableListOf()) }

            val var1 = SectionPos.sectionRelative(pos.x)
            val var2 = SectionPos.sectionRelative(pos.y)
            val var3 = SectionPos.sectionRelative(pos.z)
            val po = (var1 shl 8 or (var3 shl 4) or (var2 shl 0)).toShort()

            sectionChanges.positions.add(po)
            sectionChanges.blockData.add(net.minecraft.world.level.block.Block.stateById(getID(state[index])))
        }


        changes.entries.forEach {

            val packet = ClientboundSectionBlocksUpdatePacket(
                it.key, it.value.positions, it.value.blockData.toTypedArray()
            )

            list.sendPacket(packet)
        }

    }

}

class ChunkSectionChanges(val positions: ShortSet, val blockData: MutableList<BlockState>)