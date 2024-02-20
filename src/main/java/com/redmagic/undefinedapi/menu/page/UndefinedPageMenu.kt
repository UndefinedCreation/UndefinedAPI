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


/**
 * Represents an abstract class for an undefined page menu.
 *
 * @property title The title of the menu.
 * @property size The size of the menu.
 * @property list The list of ItemStacks for the menu.
 */
abstract class UndefinedPageMenu(private val title: String, private val size: Int, private val list: List<ItemStack>): UndefinedMenu(title, size) {

    /**
     * Represents a nullable property that holds a reference to a PageButton object.
     *
     * @property bButton The PageButton object associated with this variable.
     */
    var bButton: PageButton? = null
    /**
     * Represents a nullable variable of type `PageButton`.
     *
     * @property nButton The value of the variable.
     */
    var nButton: PageButton? = null
    /**
     * Represents a map of items with integer keys.
     */
    var itemsMap = HashMap<Int, ItemStack>()
    /**
     * Represents a nullable variable that holds a reference to a PageList object.
     *
     * The PageList class represents a list of pages for a given collection of items. The collection
     * of items is divided into pages based on a maximum number of elements per page.
     *
     * @property pageList The reference to the PageList object. It can be null if not assigned.
     */
    private var pageList: PageList<ItemStack>? = null

    /**
     * Represents a function that handles click data related to a click event.
     *
     * ClickData is a function type that takes an instance of ClickData as the receiver,
     * allowing you to access the properties of the click event, such as the slot, player,
     * item, click type, action, and inventory.
     *
     * To use this function type, you can define a lambda expression or function literal,
     * and pass it as an argument where a ClickData function type is expected.
     *
     * Example usage:
     * ```
     * clickData {
     *    */
    abstract var clickData: ClickData.() -> Unit

    /**
     * Represents the current page index of a menu.
     *
     * @property page The current page index.
     */
    private var page = 1
    /**
     * Represents the total number of pages in a menu.
     *
     * This variable is used to keep track of the total number of pages in a menu. It is updated
     * when the menu is created or when the buttons are set. The total number of pages is calculated
     * based on the number of items in the menu and the maximum number of items allowed per page.
     *
     * @property totalPages The total number of pages in the menu.
     */
    private var totalPages = 0

    /**
     * Constructs a `UndefinedPageMenu` object with the given `title`, `menuSize`, and `list`.
     *
     * @param title The title of the menu.
     * @param menuSize The size of the menu. Defaults to `MenuSize.LARGE`.
     * @param list A list of `ItemStack` objects.
     */
    constructor(title: String, menuSize: MenuSize = MenuSize.LARGE, list: List<ItemStack>) : this(
        title,
        menuSize.size,
        list
    )


    /**
     * Navigates to the next page in the menu.
     *
     * This method retrieves the items of the next page from the [PageList] and sets them in the inventory.
     * It also updates the page number, clears the inventory, and sets the buttons in the menu.
     */
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

    /**
     * Navigates to the previous page in the menu.
     */
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

    /**
     * Creates the inventory for the page menu.
     *
     * @param display The lambda function used to configure the inventory.
     * @return The created inventory.
     */
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

        setItems(pageList!!.getPage(1)!!)

        setButtons()

        return inventory!!
    }

    /**
     * Executes the specified code block when the onPress event occurs.
     *
     * @param clickData A lambda expression representing the ClickData associated with the event.
     *                  It takes an instance of [ClickData] as the receiver and does not return a value.
     */
    fun onPress(clickData: ClickData.() -> Unit){}

    /**
     * Sets the items in the menu's inventory.
     *
     * @param items The list of ItemStacks to set in the inventory.
     */
    private fun setItems(items: List<ItemStack>){

        items.forEach{
            inventory!!.setItem(inventory!!.firstEmpty(), it)
        }
    }

    /**
     * Sets the buttons for the menu inventory based on the current page and total number of pages.
     */
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

    /**
     * Sets the back button for the menu at the specified slot.
     *
     * @param slot The slot number where the back button should be placed.
     * @param backButton The back button ItemStack.
     * @param emptyButton The empty button ItemStack to replace the existing item at the slot.
     */
    fun setBackButton(slot: Int, backButton: ItemStack, emptyButton: ItemStack){
        bButton = PageButton(slot, backButton, emptyButton)
        this.itemsMap[slot] = ItemStack(Material.AIR)
    }
    /**
     * Sets the back button for the menu.
     *
     * @param pageButton The page button representing the back button.
     */
    fun setBackButton(pageButton: PageButton) {
        bButton = pageButton
        this.itemsMap[pageButton.slot] = ItemStack(Material.AIR)
    }
    /**
     * Sets the next button for the menu at the specified slot.
     *
     * @param slot The slot number where the next button should be set.
     * @param nextButton The ItemStack representing the next button.
     * @param emptyButton The ItemStack representing the empty button.
     */
    fun setNextButton(slot: Int, nextButton: ItemStack, emptyButton: ItemStack){
        nButton = PageButton(slot, nextButton, emptyButton)
        this.itemsMap[slot] = ItemStack(Material.AIR)
    }
    /**
     * Sets the next button of the UndefinedPageMenu.
     *
     * This method sets the next button of the UndefinedPageMenu to the provided `pageButton`. It also updates the `itemsMap` of the menu to replace the item in the `pageButton.slot
     *` with an `AIR` item.
     *
     * @param pageButton The `PageButton` object representing the next button.
     */
    fun setNextButton(pageButton: PageButton) {
        nButton = pageButton
        this.itemsMap[pageButton.slot] = ItemStack(Material.AIR)
    }
}
