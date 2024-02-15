package com.redmagic.undefinedapi.menu.utils

import com.redmagic.undefinedapi.builders.ItemBuilder
import org.bukkit.Material

/**
 * Utility class for creating panes with different colors.
 */
object Panes {

    /**
     * Creates a pane with the specified type of material.
     *
     * @param type the type of material to use for the pane
     * @return the created pane
     */
    private fun pane(type: Material) = ItemBuilder(type).setName(" ").build()

    /**
     * Represents a clear glass pane.
     *
     * This variable provides a convenient way to create a clear glass pane using Material.GLASS_PANE.
     * It returns a clone of the glass pane to ensure each instance is unique.
     */
    val CLEAR get() = pane(Material.GLASS_PANE).clone()
    /**
     * Represents a global constant variable named WHITE.
     *
     * The variable 'WHITE' is a read-only property that returns a cloned instance of a white stained glass pane from the
     * Material.WHITE_STAINED_GLASS_PANE.
     *
     * @return The cloned instance of the white stained glass pane.
     */
    val WHITE get() = pane(Material.WHITE_STAINED_GLASS_PANE).clone()
    /**
     * Represents a yellow stained glass pane in Minecraft.
     *
     * @return A cloned instance of yellow stained glass pane.
     */
    val YELLOW get() = pane(Material.YELLOW_STAINED_GLASS_PANE).clone()
    /**
     * Represents an orange stained glass pane.
     *
     * @return A cloned instance of the orange stained glass pane.
     */
    val ORANGE get() = pane(Material.ORANGE_STAINED_GLASS_PANE).clone()
    /**
     * Represents a red stained glass pane.
     *
     * @return A cloned instance of a red stained glass pane.
     */
    val RED get() = pane(Material.RED_STAINED_GLASS_PANE).clone()
    /**
     * Represents a Lime-stained glass pane.
     *
     * This variable provides a convenient way to obtain a clone of a lime-stained glass pane block using the LIME constant.
     * The `get` property provides the cloned instance of the LIME stained glass pane.
     *
     * @return A clone of the lime-stained glass pane block.
     */
    val LIME get() = pane(Material.LIME_STAINED_GLASS_PANE).clone()
    /**
     * Returns a cloned instance of a green stained glass pane material.
     *
     * @return A cloned instance of a green stained glass pane material.
     */
    val GREEN get() = pane(Material.GREEN_STAINED_GLASS_PANE).clone()
    /**
     * Holds the [Material.CYAN_STAINED_GLASS_PANE] cloned object.
     *
     * @return A cloned [ItemStack] object representing the cyan stained glass pane.
     */
    val CYAN get() = pane(Material.CYAN_STAINED_GLASS_PANE).clone()
    /**
     * Represents a blue stained glass pane clone.
     *
     * This variable returns a cloned instance of a blue stained glass pane, created using the provided
     * Material.LIGHT_BLUE_STAINED_GLASS_PANE.
     */
    val BLUE get() = pane(Material.LIGHT_BLUE_STAINED_GLASS_PANE).clone()
    /**
     * Represents a light blue stained glass pane.
     *
     * @return A clone of the light blue stained glass pane.
     */
    val LIGHT_BLUE get() = pane(Material.LIGHT_BLUE_STAINED_GLASS_PANE).clone()
    /**
     * Retrieves a clone of a magenta stained glass pane.
     *
     * @return A clone of the magenta stained glass pane.
     */
    val MAGENTA get() = pane(Material.MAGENTA_STAINED_GLASS_PANE).clone()
    /**
     * Returns a cloned instance of a pink stained glass pane.
     *
     * @return a cloned instance of a pink stained glass pane.
     */
    val PINK get() = pane(Material.PINK_STAINED_GLASS_PANE).clone()
    /**
     * Represents a purple stained glass pane.
     *
     * @return The cloned instance of the purple stained glass pane.
     */
    val PURPLE get() = pane(Material.PURPLE_STAINED_GLASS_PANE).clone()
    /**
     * Represents a constant variable for a light gray stained glass pane.
     *
     * @return A cloned instance of a light gray stained glass pane.
     */
    val LIGHT_GRAY get() = pane(Material.LIGHT_GRAY_STAINED_GLASS_PANE).clone()
    /**
     * A constant variable representing a gray stained glass pane.
     *
     * @return A clone of the gray stained glass pane.
     */
    val GRAY get() = pane(Material.GRAY_STAINED_GLASS_PANE).clone()
    /**
     * Retrieves a brown stained glass pane.
     *
     * @return A clone of the brown stained glass pane.
     */
    val BROWN get() = pane(Material.BROWN_STAINED_GLASS_PANE).clone()
    /**
     * Represents the constant variable `BLACK`.
     *
     * This variable is a getter that returns a cloned instance of a black stained glass pane.
     *
     * @return A cloned instance of a black stained glass pane.
     */
    val BLACK get() = pane(Material.BLACK_STAINED_GLASS_PANE).clone()

    /**
     * Represents a list of all available panes in a user interface.
     *
     * The `allPanes` variable is a private val property containing a list of pane colors. It is initialized with the following values:
     * CLEAR, WHITE, YELLOW, ORANGE, RED, LIME, GREEN, CYAN, BLUE, LIGHT_BLUE, MAGENTA, PINK, PURPLE, LIGHT_GRAY, GRAY, BROWN, BLACK.
     *
     * This list can be used to display or select different colored panes in a user interface.
     *
     * Usage Example:
     *```kotlin
     * val randomPane = allPanes.random()
     *```
     */
    private val allPanes = listOf(
        CLEAR, WHITE, YELLOW, ORANGE, RED, LIME, GREEN, CYAN, BLUE, LIGHT_BLUE,
        MAGENTA, PINK, PURPLE , LIGHT_GRAY, GRAY, BROWN, BLACK
    )

    /**
     * Generate a random element from the list of panes.
     *
     * @return A random element from the list of panes.
     */
    fun random() = allPanes.random()
}