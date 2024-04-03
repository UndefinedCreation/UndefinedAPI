package com.redmagic.undefinedapi.extension

/**
 * Returns true if the given Double value is negative.
 *
 * @return true if the Double value is negative, otherwise false.
 */
fun Double.isNegative(): Boolean = this < 0.0

/**
 * Check if the given integer is negative.
 *
 * @return `true` if the integer is negative, `false` otherwise.
 */
fun Int.isNegative(): Boolean = this < 0

/**
 * Checks if the given Double value is positive.
 *
 * @return `true` if the value is positive, `false` otherwise.
 */
fun Double.isPositive(): Boolean = this > 0

/**
 * Checks if the integer is a positive number.
 *
 * @return True if the integer is positive, false otherwise.
 */
fun Int.isPositive(): Boolean = this > 0