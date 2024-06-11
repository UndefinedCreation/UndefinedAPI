package com.redmagic.undefinedapi.nms.v1_20_4.extensions

import com.redmagic.undefinedapi.nms.extensions.getPrivateField
import com.redmagic.undefinedapi.nms.v1_20_4.SpigotNMSMappings
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.world.item.ItemStack

fun ClientboundContainerSetSlotPacket.getContainerID() = getPrivateField<Int>(SpigotNMSMappings.ClientboundContainerSetSlotPacketContairID)

fun ClientboundContainerSetSlotPacket.getContainerSlot() = getPrivateField<Int>(SpigotNMSMappings.ClientboundContainerSetSlotPacketSlot)

fun ClientboundContainerSetSlotPacket.getItemStack() = getPrivateField<ItemStack>(SpigotNMSMappings.ClientboundContainerSetSlotPacketItemStack)