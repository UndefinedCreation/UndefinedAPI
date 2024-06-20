package com.undefined.api.nms.v1_21.extensions

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_21.SpigotNMSMappings
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.world.item.ItemStack

fun ClientboundContainerSetSlotPacket.getContainerID() = getPrivateField<Int>(SpigotNMSMappings.ClientboundContainerSetSlotPacketContairID)

fun ClientboundContainerSetSlotPacket.getContainerSlot() = getPrivateField<Int>(SpigotNMSMappings.ClientboundContainerSetSlotPacketSlot)

fun ClientboundContainerSetSlotPacket.getItemStack() = getPrivateField<ItemStack>(SpigotNMSMappings.ClientboundContainerSetSlotPacketItemStack)