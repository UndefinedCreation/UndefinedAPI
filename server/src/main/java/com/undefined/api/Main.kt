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


        UndefinedCommand("Items")
            .addExecutePlayer {
                val player = player!!
                val text = api.createFakeEntity(EntityType.INTERACTION)!! as NMSInteractionEntity

                text.addViewer(player)
                text.spawn(player.location)
                text.height = 0.5f
                text.width = 2f
                text.interact { println("Interact") }

                return@addExecutePlayer false
            }

    }
}