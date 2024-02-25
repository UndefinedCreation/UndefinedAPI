package com.redmagic.undefinedapi.customEvents

import com.redmagic.undefinedapi.event.UndefinedEvent
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.data.type.TNT
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID


class PlayerHitByPlayerManager(){
    init {

        val enderCrystalMap: HashMap<EnderCrystal, UUID> = hashMapOf()

        event<EntityDamageByEntityEvent> {
            val player = entity as? Player ?: return@event
            val damagerPlayer = damager as? Player
            if (damagerPlayer != null){
                Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, damagerPlayer, HitCause.HAND))
                return@event
            }

            val projectile = damager as? Projectile
            if (projectile != null){
                if (projectile.shooter is Player)
                    Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, projectile.shooter as Player, HitCause.PROJECTILE))
            }

            val tnt = damager as? TNTPrimed
            if (tnt != null){
                if (tnt.source is Player)
                    Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, tnt.source as Player, HitCause.EXPLOSIVE))
            }

            if (enderCrystalMap.containsKey(damager)){

                val uuid = enderCrystalMap[damager]

                if (uuid != null) {
                    val crystalOwner: Player? = Bukkit.getPlayer(uuid)
                    if (crystalOwner != null) {
                        Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, crystalOwner, HitCause.END_CRYSTAL))
                    }
                }

            }

        }

        event<EntityDamageByEntityEvent> {
            val player = damager as? Player ?: return@event

            if (entity is EnderCrystal){

                enderCrystalMap[entity as EnderCrystal] = player.uniqueId

                delay(2) { enderCrystalMap.remove(entity as EnderCrystal) }

            }

        }


    }

}

class PlayerHitByPlayerEvent(val hitPlayer: Player, val damager: Player, val hitCause: HitCause): UndefinedEvent()

enum class HitCause(){
    HAND,
    PROJECTILE,
    END_CRYSTAL,
    EXPLOSIVE
}