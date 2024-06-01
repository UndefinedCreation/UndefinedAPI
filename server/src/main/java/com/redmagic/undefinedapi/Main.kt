package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.PlayerArmSwingEvent
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.extension.getNMSVersion
import com.redmagic.undefinedapi.nms.ItemSlot
import com.redmagic.undefinedapi.nms.createFakePlayer
import com.redmagic.undefinedapi.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        event<PlayerJoinEvent> {

            val npc = api.createFakePlayer("TheRedMagic", "TheRedMagic")!!
            npc.viewers.add(player)
            npc.spawn(player.location)

            delay(20) {
                npc.location = player.location.clone().add(0.0,5.0,0.0)
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

                                npc.useMainHand()
                                delay(20) {
                                    npc.kill()
                                }
                            }
                        }
                    }
                }
            }

        }

        event<PlayerArmSwingEvent> {
            println(interaction)
        }

    }
}