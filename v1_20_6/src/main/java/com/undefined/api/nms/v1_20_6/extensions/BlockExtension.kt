package com.undefined.api.nms.v1_20_6.extensions

import com.undefined.api.nms.v1_20_6.event.PacketListenerManager
import it.unimi.dsi.fastutil.shorts.ShortArraySet
import it.unimi.dsi.fastutil.shorts.ShortSet
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.block.data.CraftBlockData
import org.bukkit.entity.Player

object BlockExtension {

    fun setBlockProgress(player: Player, block: Block, stage: Int) {

        val packet = ClientboundBlockDestructionPacket(player.entityId, BlockPos(block.x, block.y, block.z), stage)

        player.sendPacket(packet)
    }

    fun getID(blockData: BlockData) : Int = net.minecraft.world.level.block.Block.getId((blockData as CraftBlockData).state)

    fun getBlockDataFromID(id: Int) : BlockData = net.minecraft.world.level.block.Block.stateById(id).createCraftBlockData()


    fun setBlockArray(state: Array<BlockData>, posList: List<Location>, list: List<Player>, persistent: Boolean) {
        val changes = mutableMapOf<SectionPos, ChunkSectionChanges>()

        state.indices.forEach { index ->
            val bPos = posList[index].let { BlockPos(it.blockX, it.blockY, it.blockZ) }
            val sectionPos = SectionPos.of(bPos)
            val sectionChanges = changes.getOrPut(sectionPos) { ChunkSectionChanges(ShortArraySet(), mutableListOf()) }

            val varX = SectionPos.sectionRelative(bPos.x)
            val varY = SectionPos.sectionRelative(bPos.y)
            val varZ = SectionPos.sectionRelative(bPos.z)
            val po = (varX shl 8 or (varZ shl 4) or varY).toShort()

            if (persistent) list.forEach { PacketListenerManager.fakeBlocks.getOrPut(it.uniqueId) { mutableListOf() }.add(bPos) }

            sectionChanges.positions.add(po)
            sectionChanges.blockData.add(net.minecraft.world.level.block.Block.stateById(getID(state[index])))
        }

        changes.entries.forEach { (key, value) ->
            val packet = ClientboundSectionBlocksUpdatePacket(key, value.positions, value.blockData.toTypedArray())
            list.sendPacket(packet)
        }
    }

    fun clearFakeBlocks(player: Player) {

        val map: MutableMap<SectionPos, MutableList<BlockPos>> = PacketListenerManager.fakeBlocks[player.uniqueId]
            ?.groupByTo(mutableMapOf(), { SectionPos.of(it) }, { it })
            ?: mutableMapOf()

        val cWorld = player.world as CraftWorld
        val light = cWorld.handle.lightEngine

        map.forEach { (key, _) ->
            val chunk = cWorld.handle.getChunk(key.chunk().x, key.chunk().z)
            val packet = ClientboundLevelChunkWithLightPacket(chunk, light, null, null, true)

            player.sendPacket(packet)
        }

        map.clear()
        PacketListenerManager.fakeBlocks.remove(player.uniqueId)
    }

}

class ChunkSectionChanges(val positions: ShortSet, val blockData: MutableList<BlockState>)