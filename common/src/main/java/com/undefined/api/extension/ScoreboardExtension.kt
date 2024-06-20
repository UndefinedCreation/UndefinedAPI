package com.undefined.api.extension

import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

/**
 * Creates a dummy objective with the given name.
 *
 * @param name the name of the dummy objective
 * @return the created dummy objective
 */
fun Scoreboard.createDummy(name: String): Objective = registerNewObjective(name, "dummy")