package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.event.PacketListenerManager1_20_4
import com.redmagic.undefinedapi.event.PacketListenerManager1_20_5
import com.redmagic.undefinedapi.event.PacketListenerManager1_20_6
import com.redmagic.undefinedapi.extension.getNMSVersion

class NMSManager {

    init {
        when(getNMSVersion()){
            "1.20.4" -> PacketListenerManager1_20_4()
            "1.20.5" -> PacketListenerManager1_20_5()
            "1.20.6" -> PacketListenerManager1_20_6()
            else ->{}
        }
    }

}