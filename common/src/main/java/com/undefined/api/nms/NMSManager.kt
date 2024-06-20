package com.undefined.api.nms

import com.undefined.api.extension.getNMSVersion


class NMSManager {

    init {
        when(getNMSVersion()){
            "1.20.4" -> com.undefined.api.nms.v1_20_4.event.PacketListenerManager()
            "1.20.5", "1.20.6" -> com.undefined.api.nms.v1_20_5.event.PacketListenerManager()
            else ->{}
        }
    }

}