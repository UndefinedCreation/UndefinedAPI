package com.redmagic.undefinedapi.nms

import com.redmagic.undefinedapi.event.PacketListenerManager1_20_4
import com.redmagic.undefinedapi.extension.getNMSVersion

class NMSManager {


    init {

        when(getNMSVersion()){
            "1.20.4" -> PacketListenerManager1_20_4()
            else ->{}
        }


    }

}