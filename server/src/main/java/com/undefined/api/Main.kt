package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.nms.createFakeEntity
import com.undefined.api.nms.interfaces.display.NMSInteractionEntity
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        val list: List<String> = listOf("String", "OtherString", "YetAnotherString")
        UndefinedCommand("test")
            .addListSubCommand({ list }, deserialize = {
                println(this.value)
                println(this.sender.name)
            })

    }
}