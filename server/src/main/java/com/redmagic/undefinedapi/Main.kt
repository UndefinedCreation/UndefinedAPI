package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.*
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.extension.glow
import com.redmagic.undefinedapi.nms.ItemSlot
import com.redmagic.undefinedapi.nms.createFakeEntity
import com.redmagic.undefinedapi.nms.createFakePlayer
import com.redmagic.undefinedapi.nms.getTexture
import com.redmagic.undefinedapi.nms.interfaces.NMSItemEntity
import com.redmagic.undefinedapi.nms.interfaces.NMSPlayer
import com.redmagic.undefinedapi.nms.interfaces.NMSSlimeEntity
import com.redmagic.undefinedapi.scheduler.TimeUnit
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

        event<PlayerJoinEvent> {

            val p = api.createFakeEntity(EntityType.PLAYER, "the", "the")!! as NMSPlayer

            p.addViewer(player)
            p.spawn(player.location)

            p.setItem(ItemSlot.MAINHAND, ItemStack(Material.SHIELD))
            p.useMainHand()

            delay(100) {
                p.stopUsingMainHandItem()
                println("stop")
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