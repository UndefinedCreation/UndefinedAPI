package com.undefined.api.menu

/**
 * Enumeration representing the size options for a menu.
 *
 * @property size The size value of the menu.
 */
enum class MenuSize(val size: Int) {

    TINY(9),
    /**
     * Enumeration for the size of a menu.
     *
     * @param size The size value of the menu.
     */
    PETITE(18),
    /**
     * Represents the MINI size menu with a size of 27.
     *
     * @param size The size of the menu, which is 27.
     */
    MINI(27),
    /**
     * Enum class representing the small size menu.
     *
     * This enum class represents the small size menu in a restaurant. It is one of the
     * available sizes for a menu.
     *
     * @property size The integer value representing the size of the menu.
     */
    SMALL(36),
    /**
     * A display size option for a menu that is compact.
     *
     * @property size The size value associated with the COMPACT menu size option.
     */
    COMPACT(45),
    /**
     * Represents a large menu size.
     *
     * @param size the size of the menu.
     */
    LARGE(54)

}