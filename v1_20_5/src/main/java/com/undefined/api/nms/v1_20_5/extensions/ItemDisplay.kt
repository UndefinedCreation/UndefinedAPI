package com.undefined.api.nms.v1_20_5.extensions

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_20_5.SpigotNMSMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Display.ItemDisplay

fun ItemDisplay.DATA_ITEM_DISPLAY_ID(): EntityDataAccessor<Byte> = getPrivateField(SpigotNMSMappings.ItemDisplayID)