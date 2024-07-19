package com.undefined.api.customEvents.entity.player

import com.undefined.api.event.UndefinedEvent
import com.undefined.api.event.event
import com.undefined.api.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.UUID


/**
 * @hidden
 */
class PlayerHitByPlayerManager(){
    init {

        val enderCrystalMap: HashMap<EnderCrystal, UUID> = hashMapOf()

        event<EntityDamageByEntityEvent> (priority = EventPriority.HIGHEST) {
            if (isCancelled) return@event
            val player = entity as? Player ?: return@event
            val damagerPlayer = damager as? Player
            if (damagerPlayer != null){
                if (damagerPlayer == player) return@event
                Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, damagerPlayer, HitCause.HAND))
                return@event
            }

            val projectile = damager as? Projectile
            if (projectile != null){
                if (projectile.shooter is Player) {
                    if (projectile.shooter as Player == player) return@event
                    Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, projectile.shooter as Player, HitCause.PROJECTILE))
                }
            }

            val tnt = damager as? TNTPrimed
            if (tnt != null){
                if (tnt.source is Player)
                    if (tnt.source == player) return@event
                    Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, tnt.source as Player, HitCause.EXPLOSIVE))
            }

            if (enderCrystalMap.containsKey(damager)){

                val uuid = enderCrystalMap[damager]

                if (uuid != null) {
                    val crystalOwner: Player? = Bukkit.getPlayer(uuid)
                    if (crystalOwner != null) {
                        if (crystalOwner == player) return@event
                        Bukkit.getPluginManager().callEvent(PlayerHitByPlayerEvent(player, crystalOwner, HitCause.END_CRYSTAL))
                    }
                }

            }

        }

        event<EntityDamageByEntityEvent> (priority = EventPriority.HIGHEST) {
            if (isCancelled) return@event
            val player = damager as? Player ?: return@event

            if (entity is EnderCrystal){

                enderCrystalMap[entity as EnderCrystal] = player.uniqueId
                delay(2) { enderCrystalMap.remove(entity as EnderCrystal) }

            }

        }


    }

}

/**
 * Represents an event that occurs when a player is hit by another player.
 *
 * @param hitPlayer The player who was hit.
 * @param damager The player who caused the hit.
 * @param hitCause The cause of the hit.
 */
class PlayerHitByPlayerEvent(val hitPlayer: Player, val damager: Player, val hitCause: HitCause): UndefinedEvent()

/**
 * Enum class representing the cause of a hit.
 *
 */
enum class HitCause(){
    HAND,
    PROJECTILE,
    END_CRYSTAL,
    EXPLOSIVE
}