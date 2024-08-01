package com.undefined.api.nms.v1_21.extensions

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_21.SpigotNMSMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Display.ItemDisplay

fun ItemDisplay.DATA_ITEM_DISPLAY_ID(): EntityDataAccessor<Byte> = getPrivateField(SpigotNMSMappings.ItemDisplayID)