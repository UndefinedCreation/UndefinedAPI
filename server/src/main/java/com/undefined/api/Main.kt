package com.undefined.api

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.event.event
import com.undefined.api.extension.asBlockData
import com.undefined.api.extension.getID
import com.undefined.api.extension.glow
import com.undefined.api.extension.string.translateColor
import com.undefined.api.nms.ItemSlot
import com.undefined.api.nms.createFakeEntity
import com.undefined.api.nms.createFakePlayer
import com.undefined.api.nms.interfaces.NMSBlockDisplayEntity
import com.undefined.api.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class Main: JavaPlugin() {

    lateinit var api: UndefinedAPI

    override fun onEnable() {
        api = UndefinedAPI(this)

        val list: MutableList<UUID> = mutableListOf(UUID.randomUUID())

        val data = Material.OAK_LEAVES.createBlockData()

        println(data.asString)

        val id = data.getID() + 1

        println(id)

        val a = id.asBlockData()

        println(a.asString)

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