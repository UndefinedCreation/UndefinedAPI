package com.undefined.api.nms.v1_20_6.extensions

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_20_6.SpigotNMSMappings
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.world.item.ItemStack

fun ClientboundContainerSetSlotPacket.getContainerID() = getPrivateField<Int>(com.undefined.api.nms.v1_20_6.SpigotNMSMappings.ClientboundContainerSetSlotPacketContairID)

fun ClientboundContainerSetSlotPacket.getContainerSlot() = getPrivateField<Int>(com.undefined.api.nms.v1_20_6.SpigotNMSMappings.ClientboundContainerSetSlotPacketSlot)

fun ClientboundContainerSetSlotPacket.getItemStack() = getPrivateField<ItemStack>(com.undefined.api.nms.v1_20_6.SpigotNMSMappings.ClientboundContainerSetSlotPacketItemStack)