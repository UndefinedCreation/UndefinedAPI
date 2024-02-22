package com.redmagic.undefinedapi.extension

import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

fun Scoreboard.createDummy(name: String): Objective = registerNewObjective(name, "dummy")