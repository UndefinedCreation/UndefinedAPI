package com.undefined.api.nms

import com.undefined.api.extension.getNMSVersion

class NMSManager {

    init {
        when(getNMSVersion()) {
            "1.20.4" -> com.undefined.api.nms.v1_20_4.event.PacketListenerManager()
            "1.20.6" -> com.undefined.api.nms.v1_20_6.event.PacketListenerManager()
            "1.21", "1.21.1" -> com.undefined.api.nms.v1_21.event.PacketListenerManager()
        }
    }

}