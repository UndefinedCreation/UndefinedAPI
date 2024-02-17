package com.redmagic.undefinedapi.menu.page

import java.util.*
import kotlin.math.ceil
import kotlin.math.min


class PageList<ItemStack>(collection: Collection<ItemStack>, val maxElement: Int): ArrayList<ItemStack>(collection) {

    fun pageCount(): Int = ceil(size.toDouble() / maxElement).toInt()
    fun getPage(page: Int): List<ItemStack>?{
        if (page < 1 || page > pageCount()) return null;
        val startIndex = (page - 1) * maxElement
        val endIndex = min(startIndex + maxElement, size)
        return subList(startIndex, endIndex)
    }

    fun addAll(t: Array<ItemStack>) {
        Collections.addAll(this, *t)
    }
}