package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.extension.sendActionBar
import com.undefined.api.menu.MenuManager.openMenu
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.page.UndefinedPageMenu
import com.undefined.api.scheduler.TimeUnit
import com.undefined.api.utils.item.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: Main
    }
    lateinit var api: UndefinedAPI
    var value = mutableListOf(
        ItemBuilder(Material.EMERALD)
            .setName("string")
            .build()
    )

    override fun onEnable() {
        INSTANCE = this
        api = UndefinedAPI(this)

        val page = TestPage()
        UndefinedCommand("test")
            .addExecutePlayer {
                sendActionBar(Component.text("hi!"), 1, TimeUnit.SECONDS)
                openMenu(page)
                return@addExecutePlayer true
            }
    }
}