package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.customEvents.ParticleEvent
import com.undefined.api.customEvents.SoundEvent
import com.undefined.api.customEvents.SoundStopEvent
import com.undefined.api.event.event
import com.undefined.api.extension.asBlockData
import com.undefined.api.extension.getID
import com.undefined.api.extension.glow
import com.undefined.api.extension.sendBlockUpdateArray
import com.undefined.api.extension.string.translateColor
import com.undefined.api.nms.ItemSlot
import com.undefined.api.nms.createFakeEntity
import com.undefined.api.nms.createFakePlayer
import com.undefined.api.nms.interfaces.NMSBlockDisplayEntity
import com.undefined.api.nms.interfaces.NMSPlayer
import com.undefined.api.scheduler.delay
import com.undefined.api.scheduler.repeatingTask
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Particle.DustTransition
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.noise.PerlinNoiseGenerator
import java.util.UUID
import kotlin.random.Random

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


    }

    fun test(){


        event<PlayerJoinEvent> {

            println("join")

            val npc = api.createFakePlayer("TheRedMagic", "TheRedMagic")!!
            npc.addViewer(player)
            npc.spawn(player.location)
            val newLoc = player.location.clone().add(0.0,5.0,0.0)
            npc.moveOrTeleport(newLoc)


            com.undefined.api.scheduler.delay(20) {
                npc.teleport(newLoc)
                com.undefined.api.scheduler.delay(20) {
                    npc.name = "Jeff"
                    npc.isCrouching = true
                    com.undefined.api.scheduler.delay(20) {
                        npc.isSwimming = true
                        com.undefined.api.scheduler.delay(20) {
                            npc.isCrouching = false
                            npc.onFire = true
                            com.undefined.api.scheduler.delay(20) {
                                npc.onFire = false

                                npc.setItem(ItemSlot.MAINHAND, ItemStack(Material.SHIELD))

                                npc.moveMainHand()

                                npc.moveOffHand()
                                com.undefined.api.scheduler.delay(20) {
                                    npc.kill()
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}