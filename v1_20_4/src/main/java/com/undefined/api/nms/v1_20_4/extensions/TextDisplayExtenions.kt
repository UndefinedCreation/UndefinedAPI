package com.undefined.api.nms.v1_20_4.extensions

import com.undefined.api.nms.extensions.getPrivateField
import com.undefined.api.nms.v1_20_4.SpigotNMSMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Display.TextDisplay

fun TextDisplay.DATA_BACKGROUND_COLOR_ID(): EntityDataAccessor<Int> = getPrivateField(SpigotNMSMappings.TextDisplayBackgroundID)