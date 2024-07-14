package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.event.event
import com.undefined.api.extension.sendBlockUpdateArray
import com.undefined.api.extension.sendClearFakeBlock
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

           val locList: MutableList<Location> = mutableListOf()
           val blockList: MutableList<BlockData> = mutableListOf()

           for (x in -100..100) {
               for (z in -100..100) {
                   val p = PerlinNoiseGenerator.getInstance()

                   val location = player.location.clone().add(x.toDouble(), 0.0, z.toDouble())
                   val b = location.add(0.0,p.noise(location.x, location.y, location.z) * 2, 0.0)
                   locList.add(b)
                   blockList.add(Material.PISTON.createBlockData())
               }
           }

           delay(60) {
               player.sendBlockUpdateArray(locList, blockList)
           }


       }

        UndefinedCommand("clearFake")
            .addExecutePlayer {
                player!!.sendClearFakeBlock()
                return@addExecutePlayer false
            }


    }


}