package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.extension.getNMSVersion


class NMSManager {

    init {
        when(getNMSVersion()){
            "1.20.4" -> com.redmagic.undefinedapi.nms.v1_20_4.event.PacketListenerManager()
            "1.20.5" -> com.redmagic.undefinedapi.nms.v1_20_5.event.PacketListenerManager()
            "1.20.6" -> com.redmagic.undefinedapi.nms.v1_20_6.event.PacketListenerManager()
            else ->{}
        }
    }

}