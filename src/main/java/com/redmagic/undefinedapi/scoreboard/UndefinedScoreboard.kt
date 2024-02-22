package com.redmagic.undefinedapi.scoreboard

import org.bukkit.Bukkit
import org.bukkit.scoreboard.Scoreboard

abstract class UndefinedScoreboard {

    val settings = Settings()

    var emptyLines: Int = 0




    fun createScoreboard(consmer: Scoreboard.() -> Unit): Scoreboard{
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard



        return scoreboard
    }

    abstract fun generateScoreboard(): Scoreboard


    fun addLine(content: String){

    }

}

class Settings(var numbers: Boolean = false)