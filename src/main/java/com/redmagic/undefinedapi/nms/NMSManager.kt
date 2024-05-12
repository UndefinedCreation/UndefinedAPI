package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.nms.extension.getNMSVersion
import com.redmagic.undefinedapi.nms.extension.isRemapped
import com.redmagic.undefinedapi.nms.minecraftVersion.v1_20_4.event.PacketListenerManager1_20_4

class NMSManager {


    init {

        if (isRemapped()){
            when(getNMSVersion()){
                "1.20.4" -> PacketListenerManager1_20_4()
                else ->{}
            }
        }


    }

}