package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.nms.triggerTotem
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        UndefinedCommand("test")
            .addExecutePlayer {
                this.triggerTotem()
                return@addExecutePlayer false
            }

    }
}