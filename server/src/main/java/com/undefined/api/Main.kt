package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.customEvents.block.BlockGroupUpdateEvent
import com.undefined.api.customEvents.block.BlockUpdateEvent
import com.undefined.api.event.event
import com.undefined.api.extension.sendBlockUpdateArray
import com.undefined.api.extension.sendClearFakeBlock
import com.undefined.api.extension.string.asInventory
import com.undefined.api.extension.string.asString
import com.undefined.api.scheduler.delay
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.noise.PerlinNoiseGenerator

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)


        event<PlayerJoinEvent> {

            val invString = player.inventory.asString()

            val inv = invString.asInventory()

        }

    }


}