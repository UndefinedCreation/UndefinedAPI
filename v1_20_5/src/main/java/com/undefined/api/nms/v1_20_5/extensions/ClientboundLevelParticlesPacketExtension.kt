package com.undefined.api.nms.v1_20_5.extensions

import net.minecraft.core.particles.*
import org.bukkit.Particle.DustOptions
import org.bukkit.Particle.DustTransition
import org.bukkit.craftbukkit.inventory.CraftItemStack

fun ParticleOptions.getBukkitOptions(): Any? =
    when(this) {
        is DustColorTransitionOptions -> DustTransition(
            org.bukkit.Color.fromBGR(
                fromColor.x.toInt() * 255,
                fromColor.y.toInt() * 255,
                fromColor.z.toInt() * 255
            ),
            org.bukkit.Color.fromBGR(
                toColor.x.toInt() * 255,
                toColor.y.toInt() * 255,
                toColor.z.toInt() * 255
            ), scale
        )

        is DustParticleOptions -> DustOptions(
            org.bukkit.Color.fromBGR(
                color.x.toInt() * 255,
                color.y.toInt() * 255,
                color.z.toInt() * 255
            ),
            scale
        )

        is ItemParticleOption -> CraftItemStack.asBukkitCopy(item)
        is BlockParticleOption -> state.createCraftBlockData()
        is SculkChargeParticleOptions -> roll
        else -> null
    }