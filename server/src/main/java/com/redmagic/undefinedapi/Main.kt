package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.*
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.nms.ItemSlot
import com.redmagic.undefinedapi.nms.createFakeEntity
import com.redmagic.undefinedapi.nms.createFakePlayer
import com.redmagic.undefinedapi.scheduler.delay
import com.redmagic.undefinedapi.scheduler.repeatingTask
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        event<PlayerUseItemEvent> {
            println(this.offhand)
            println(this.starting)
        }

        println(EntityType.DROPPED_ITEM.name)

        event<PlayerJoinEvent> {


            val e = api.createFakeEntity(EntityType.ENDER_DRAGON)!!
            e.addViewer(player)

            e.spawn(player.location)

            e.glowing = true
            e.isVisible = false

            repeatingTask(5) {
                e.glowingColor = ChatColor.entries.toTypedArray().random()
            }

        }

    }

    fun test(player : Player){

        val npc = api.createFakePlayer("the", "the")!!
        npc.viewers.add(player)

        npc.spawn(player.location)

        npc.onFire = true


        repeatingTask(1) {
            npc.teleport(player.location)
        }

        event<PlayerJoinEvent> {

            val npc = api.createFakePlayer("TheRedMagic", "TheRedMagic")!!
            npc.viewers.remove(player)

            val newLoc = Location(Bukkit.getWorld("world"), 10.0, 10.0, 10.0)

            npc.moveOrTeleport(newLoc)

            npc.name = "NewName"

            npc.resetPose()

            delay(20) {
                npc.teleport(newLoc)
                delay(20) {
                    npc.name = "Jeff"
                    npc.isCrouching = true
                    delay(20) {
                        npc.isSwimming = true
                        delay(20) {
                            npc.isCrouching = false
                            npc.onFire = true
                            delay(20) {
                                npc.onFire = false

                                npc.setItem(ItemSlot.MAINHAND, ItemStack(Material.SHIELD))

                                npc.moveMainHand()

                                npc.moveOffHand()
                                delay(20) {
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