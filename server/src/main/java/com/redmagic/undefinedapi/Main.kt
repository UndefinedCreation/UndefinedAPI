package com.redmagic.undefinedapi

import com.redmagic.undefinedapi.customEvents.PlayerArmorChangeEvent
import com.redmagic.undefinedapi.customEvents.PlayerExtinguishEvent
import com.redmagic.undefinedapi.customEvents.PlayerIgniteEvent
import com.redmagic.undefinedapi.customEvents.PlayerMainHandSwitchEvent
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.nms.ClickType
import com.redmagic.undefinedapi.nms.ItemSlot
import com.redmagic.undefinedapi.nms.createFakePlayer
import com.redmagic.undefinedapi.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockDamageEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        event<PlayerMainHandSwitchEvent> {
            println(itemStack)
        }

    }

    fun test(player : Player){
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