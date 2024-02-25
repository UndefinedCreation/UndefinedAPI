package com.redmagic.undefinedapi.scoreboard

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

/**
 * Represents a scoreboard with undefined lines and values.
 *
 * @property title The title of the scoreboard.
 * @property scoreboard The scoreboard instance.
 * @property objective The objective of the scoreboard.
 * @property index The current index value for lines.
 * @property spaces The number of spaces.
 * @property teamMap The map of teams identified by an integer key.
 *
 * @constructor Creates a new UndefinedScoreboard object with the given title and scoreboard instance.
 * @param title The title of the scoreboard.
 * @param scoreboard The scoreboard instance.
 *
 * @constructor Creates a new UndefinedScoreboard object with the given title.
 * @param title The title of the scoreboard.
 *
 * @constructor Creates a new UndefinedScoreboard object with the given title and a new scoreboard instance.
 * @param title The title of the scoreboard.
 *
 * @constructor Creates a new UndefinedScoreboard object with the given title and scoreboard instance.
 * @param title The title of the scoreboard.
 * @param scoreboard The scoreboard instance.
 */
class UndefinedScoreboard(private val title: Component, private val  scoreboard: Scoreboard) {

    /**
     * Creates a new object of type "Scoreboard" with the given title.
     *
     * @param title the title of the scoreboard
     */
    constructor(title: String): this(Component.text(title), Bukkit.getScoreboardManager().newScoreboard)

    /**
     * Creates a new instance of the class with the specified title and a new scoreboard.
     *
     * @param title the title of the component
     */
    constructor(title: Component): this(title, Bukkit.getScoreboardManager().newScoreboard)

    /**
     * Constructs a new instance of the class with the given title and scoreboard.
     *
     * @param title The title for the component.
     * @param scoreboard The scoreboard instance to use.
     */
    constructor(title: String, scoreboard: Scoreboard): this(Component.text(title), scoreboard)

    /**
     * Represents the objective for a scoreboard.
     *
     * @property objective The registered objective.
     *
     * @param title The title of the objective.
     * @param type The type of the objective.
     */
    private val objective = scoreboard.registerNewObjective(title.examinableName(), "dummy")

    /**
     * The index variable represents an integer value used for indexing purposes.
     *
     * @property index The current value of the index.
     */
    private var index: Int = 0
    /**
     * The number of spaces.
     */
    private var spaces: Int = 0
    /**
     * Represents a map of teams identified by an integer key.
     *
     * This map stores teams as values, with integer keys used to uniquely identify each team.
     * The map uses a [HashMap] implementation to provide efficient key-value pair storage and retrieval.
     *
     * @property teamMap The underlying [HashMap] that stores the teams.
     *
     * @see Team
     * @see HashMap
     */
    private val teamMap: HashMap<Int, Team> = HashMap()

    init {
        objective.displayName(title)
        objective.displaySlot = DisplaySlot.SIDEBAR
    }

    /**
     * Adds an empty line to the scoreboard.
     *
     * @return The current `UndefinedScoreboard` instance.
     */
    fun addEmptyLine(): UndefinedScoreboard{
        val string = " ".repeat(spaces)

        val score = objective.getScore(order(index) + string)
        score.score = 0

        spaces++
        index++
        return this
    }

    /**
     * Adds a line to the scoreboard.
     *
     * @param string the string to be added as a line to the scoreboard.
     * @return an instance of `UndefinedScoreboard` that represents the modified scoreboard.
     */
    fun addLine(string: String): UndefinedScoreboard {
        val score = objective.getScore(order(index) + ChatColor.RESET + string)
        score.score = 0
        index++
        return this
    }

    /**
     * Sets the title of the scoreboard.
     *
     * @param title the title component to set
     * @return the updated UndefinedScoreboard instance
     */
    fun setTitle(title: Component): UndefinedScoreboard {
        objective.displayName(title)
        return this
    }
    /**
     * Sets the title of the UndefinedScoreboard.
     *
     * @param string the title text to be set
     * @return the UndefinedScoreboard with the updated title
     */
    fun setTitle(string: String): UndefinedScoreboard  = setTitle(Component.text(string))

    /**
     * Adds a value line to the undefined scoreboard with the specified parameters.
     *
     * @param id the ID of the value line
     * @param prefix the prefix text for the value line
     * @param suffix the suffix text for the value line
     * @return the modified undefined scoreboard
     */
    fun addValueLine(id: Int, prefix: String, suffix: String): UndefinedScoreboard = addValueLine(id, Component.text(prefix), Component.text(suffix))
    /**
     * Adds a new value line to the scoreboard.
     *
     * @param id The ID of the value line.
     * @param prefix The prefix component to be displayed before the value.
     * @param suffix The suffix component to be displayed after the value.
     * @return The UndefinedScoreboard instance after adding the value line.
     */
    fun addValueLine(id: Int, prefix: Component, suffix: Component): UndefinedScoreboard{

        val order = order(index)
        val team = scoreboard.registerNewTeam(id.toString())

        team.addEntry(order)
        team.prefix(prefix)
        team.suffix(suffix)

        objective.getScore(order).score = 0

        teamMap[id] = team
        index++
        return this
    }

    /**
     * Sets the prefix and suffix for the scoreboard value line for the specified ID.
     *
     * @param id The ID of the value line in the scoreboard.
     * @param prefix The prefix component to be displayed before the value.
     * @param suffix The suffix component to be displayed after the value.
     * @return The undefined scoreboard object with the updated value line.
     */
    fun setValueLine(id: Int, prefix: Component? = null, suffix: Component? = null): UndefinedScoreboard {

        val team = teamMap[id] ?: return this

        if (prefix != null)
            team.prefix(prefix)

        if (suffix != null)
            team.suffix(suffix)

        return this
    }

    /**
     * Sets the value line of the scoreboard with the specified ID, prefix, and suffix.
     *
     * @param id The ID of the scoreboard to set the value line for.
     * @param prefix The optional prefix text to display before the value line.
     * @param suffix The optional suffix text to display after the value line.
     * @return An instance of UndefinedScoreboard with the updated value line.
     */
    fun setValueLine(id: Int, prefix: String? = null, suffix: String? = null): UndefinedScoreboard = setValueLine(id, Component.text(prefix!!), Component.text(suffix!!))


    /**
     * Generates an order string based on the given time.
     *
     * @param time the time to generate the order string for
     * @return the generated order string
     */
    private fun order(time: Int): String {
        return "§" + ('a'.code + time).toChar().toString() + ChatColor.RESET
    }
}
