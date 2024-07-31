package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.customEvents.block.BlockGroupUpdateEvent
import com.undefined.api.customEvents.block.BlockUpdateEvent
import com.undefined.api.event.event
import com.undefined.api.extension.sendBlockUpdateArray
import com.undefined.api.extension.sendClearFakeBlock
import com.undefined.api.extension.string.asInventory
import com.undefined.api.extension.string.asString
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.normal.UndefinedMenu
import com.undefined.api.nms.createFakeEntity
import com.undefined.api.scheduler.delay
import com.undefined.api.scoreboard.UndefinedScoreboard
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.noise.PerlinNoiseGenerator

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)


        event<PlayerJoinEvent> {

            val text = api.createFakeEntity(EntityType.TEXT_DISPLAY, "Testing")!!
            text.addViewer(player)
            text.spawn(player.location)

        }

    }
}