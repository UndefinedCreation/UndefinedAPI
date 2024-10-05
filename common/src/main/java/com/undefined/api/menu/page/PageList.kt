package com.undefined.api.menu.page

import java.util.*
import kotlin.math.ceil
import kotlin.math.min


/**
 * Represents a list of pages for a given collection of items.
 * The collection of items is divided into pages based on a maximum number of elements per page.
 *
 * @param ItemStack The type of elements in the page list.
 * @property collection The initial collection of items to be divided into pages.
 * @property maxElement The maximum number of elements allowed per page.
 */
class PageList<ItemStack>(collection: Collection<ItemStack>, val maxElement: Int): ArrayList<ItemStack>(collection) {

    /**
     * Calculates the number of pages needed to display the elements in the collection based on the maximum number of elements per page.
     *
     * @return The number of pages.
     */
    fun pageCount(): Int = ceil(size.toDouble() / maxElement).toInt()

    /**
     * Retrieves a page of items from a page list.
     *
     * @param page The page number to retrieve.
     * @return The list of items on the requested page, or null if the page number is out of range.
     */
    fun getPage(page: Int): List<ItemStack>?{
        if (page < 1 || page > pageCount()) return null;
        if (isEmpty()) return null
        val startIndex = (page - 1) * maxElement
        val endIndex = min(startIndex + maxElement, size)
        return subList(startIndex, endIndex)
    }

    /**
     * Adds all elements from the given array to the PageList.
     *
     * @param t an array of elements of type [ItemStack] to be added.
     */
    fun addAll(t: Array<ItemStack>) {
        Collections.addAll(this, *t)
    }

}