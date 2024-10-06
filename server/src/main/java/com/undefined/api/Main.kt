package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.extension.sendActionBar
import com.undefined.api.scheduler.TimeUnit
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        UndefinedCommand("test")
            .addIncorrectMessage("test")
            .addSubCommand("hi")
            .addIncorrectMessage("hi")
            .addSubCommand("hi")
            .addIncorrectMessage("hi")
            .addExecutePlayer {
                sendActionBar(Component.text("hi!"), 1, TimeUnit.SECONDS)
                return@addExecutePlayer true
            }
    }
}