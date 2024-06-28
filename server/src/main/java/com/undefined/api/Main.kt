package com.undefined.api

import com.undefined.api.event.event
import com.undefined.api.nms.ItemSlot
import com.undefined.api.nms.createFakeEntity
import com.undefined.api.nms.createFakePlayer
import com.undefined.api.nms.interfaces.NMSBlockDisplayEntity
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        event<PlayerJoinEvent> {

            val b = api.createFakeEntity(EntityType.BLOCK_DISPLAY, Material.STONE.createBlockData())!! as NMSBlockDisplayEntity

            b.addViewer(player)

            b.spawn(player.location)

            b.scale(5f)

            b.leftRotation(25f,25f,25f)

            b.offsetY = 25f

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