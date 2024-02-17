package com.redmagic.undefinedapi.menu.page

import com.google.gson.annotations.Until
import com.redmagic.undefinedapi.extension.string.emptySlots
import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.UndefinedMenu
import com.redmagic.undefinedapi.menu.normal.button.ClickData
import com.redmagic.undefinedapi.menu.page.button.PageButton
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.MustBeInvokedByOverriders


abstract class UndefinedPageMenu(private val title: String, private val size: Int, private val list: List<ItemStack>): UndefinedMenu(title, size) {

    var bButton: PageButton? = null
    var nButton: PageButton? = null
    var itemsMap = HashMap<Int, ItemStack>()
    private var pageList: PageList<ItemStack>? = null

    abstract var clickData: ClickData.() -> Unit

    private var page = 1
    private var totalPages = 0

    constructor(title: String, menuSize: MenuSize = MenuSize.LARGE, list: List<ItemStack>) : this(
        title,
        menuSize.size,
        list
    )


    fun nextPage(){

        val items = pageList?.getPage(page + 1) ?: return
        page++;
        inventory!!.clear()

        itemsMap.forEach{
            inventory?.setItem(it.key, it.value)
        }

        setButtons()
        setItems(items)

    }

    fun previousPage() {


        val items: List<ItemStack> = pageList?.getPage(page - 1) ?: return
        page--;

        inventory!!.clear()

        itemsMap.forEach { it: Map.Entry<Int, ItemStack> ->
            inventory?.setItem(it.key, it.value)
        }

        setButtons()
        setItems(items)
    }

    fun createPageInventory(display: Inventory.() -> Unit): Inventory{


        inventory = Bukkit.createInventory(null, size, title).apply(display)

        inventory!!.contents.forEach {
            if (it != null)
                itemsMap[inventory!!.contents.indexOf(it)] = it
        }

        inventory!!.setItem(bButton!!.slot, bButton!!.activeButton)
        inventory!!.setItem(nButton!!.slot, nButton!!.activeButton)

        pageList = PageList(list, inventory!!.emptySlots())
        totalPages = pageList!!.pageCount()

        setButtons()

        setItems(pageList!!.getPage(1)!!)

        return inventory!!
    }

    fun onPress(clickData: ClickData.() -> Unit){}

    private fun setItems(items: List<ItemStack>){

        items.forEach{
            inventory!!.setItem(inventory!!.firstEmpty(), it)
        }
    }

    private fun setButtons(){
        if (page + 1 > totalPages){
            inventory!!.setItem(nButton!!.slot, nButton!!.emptyButton)
        }else {
            inventory!!.setItem(nButton!!.slot, nButton!!.activeButton)
        }

        if (page - 1 <= 0){
            inventory!!.setItem(bButton!!.slot, bButton!!.emptyButton)
        }else{
            inventory!!.setItem(bButton!!.slot, bButton!!.activeButton)
        }
    }

    fun setBackButton(slot: Int, backButton: ItemStack, emptyButton: ItemStack){
        bButton = PageButton(slot, backButton, emptyButton)
        this.itemsMap[slot] = ItemStack(Material.AIR)
    }
    fun setBackButton(pageButton: PageButton) {
        bButton = pageButton
        this.itemsMap[pageButton.slot] = ItemStack(Material.AIR)
    }
    fun setNextButton(slot: Int, nextButton: ItemStack, emptyButton: ItemStack){
        nButton = PageButton(slot, nextButton, emptyButton)
        this.itemsMap[slot] = ItemStack(Material.AIR)
    }
    fun setNextButton(pageButton: PageButton) {
        nButton = pageButton
        this.itemsMap[pageButton.slot] = ItemStack(Material.AIR)
    }
}
