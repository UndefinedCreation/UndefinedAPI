package com.redmagic.undefinedapi.scoreboard

import com.redmagic.undefinedapi.extension.createDummy
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class UndefinedScoreboard(private val title: Component, private val  scoreboard: Scoreboard) {

    constructor(title: String): this(Component.text(title), Bukkit.getScoreboardManager().newScoreboard)

    constructor(title: Component): this(title, Bukkit.getScoreboardManager().newScoreboard)

    constructor(title: String, scoreboard: Scoreboard): this(Component.text(title), scoreboard)

    private val objective = scoreboard.createDummy(title.toString())

    private var index: Int = 0
    private var spaces: Int = 0
    private val teamMap: HashMap<Int, Team> = HashMap()

    init {
        objective.displayName(title)
    }

    fun addEmptyLine(): UndefinedScoreboard{
        val string = " ".repeat(spaces)

        val score = objective.getScore(order(index) + string)
        score.score = 0

        spaces++
        index++
        return this
    }

    fun addLine(string: String): UndefinedScoreboard {
        val score = objective.getScore(order(index) + ChatColor.RESET + string)
        score.score = 0
        index++
        return this
    }

    fun setTitle(title: Component): UndefinedScoreboard {
        objective.displayName(title)
        return this
    }
    fun setTitle(string: String): UndefinedScoreboard  = setTitle(Component.text(string))

    fun addValueLine(id: Int, prefix: String, suffix: String): UndefinedScoreboard = addValueLine(id, Component.text(prefix), Component.text(suffix))
    fun addValueLine(id: Int, prefix: Component, suffix: Component): UndefinedScoreboard{

        val order = order(index)
        val team = scoreboard.registerNewTeam(id.toString())

        team.addEntry(order + id.toString())
        team.prefix(prefix)
        team.suffix(suffix)

        objective.getScore(order + id.toString()).score = 0

        teamMap[id] = team
        index++
        return this
    }

    fun setValueLine(id: Int, prefix: Component? = null, suffix: Component? = null): UndefinedScoreboard {

        val team = teamMap[id] ?: return this

        if (prefix != null)
            team.prefix(prefix)

        if (suffix != null)
            team.suffix(suffix)

        return this
    }

    fun setValueLine(id: Int, prefix: String? = null, suffix: String? = null): UndefinedScoreboard = setValueLine(id, prefix, suffix)


    private fun order(time: Int): String {
        return "§" + ('a'.code + time).toChar().toString() + ChatColor.RESET
    }
}
