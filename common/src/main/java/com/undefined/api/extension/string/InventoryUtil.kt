package com.undefined.api.extension.string

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Converts the inventory to a Base64 encoded string representation.
 *
 * @return The Base64 encoded string representation of the inventory.
 */
fun Inventory.asString(): String {

    val outputStream = ByteArrayOutputStream()
    val dataOutput = BukkitObjectOutputStream(outputStream)

    dataOutput.writeInt(this.size)

    contents.forEach { itemStack ->
        dataOutput.writeObject(itemStack)
    }

    dataOutput.close()
    return Base64Coder.encodeLines(outputStream.toByteArray())

}

/**
 * Converts the ItemStack object to a Base64 encoded string representation.
 *
 * @return The Base64 encoded string representation of the ItemStack.
 */
fun ItemStack.asString(): String {
    val outputStream = ByteArrayOutputStream()
    val dataOutput = BukkitObjectOutputStream(outputStream)

    dataOutput.writeObject(this)
    dataOutput.close()
    return Base64Coder.encodeLines(outputStream.toByteArray())
}

/**
 * Converts a Base64 encoded String into an ItemStack.
 *
 * @return The ItemStack represented by the Base64 encoded String.
 */
fun String.asItemStack(): ItemStack {
    val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(this))
    val dataInput = BukkitObjectInputStream(inputStream)
    val itemStack = dataInput.readObject() as ItemStack
    dataInput.close()
    return itemStack
}

/**
 * Converts a Base64-encoded string to an inventory object.
 *
 * @return The converted inventory object.
 */
fun String.asInventory(): Inventory {
    val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(this))
    val dataInput = BukkitObjectInputStream(inputStream)
    val size = dataInput.readInt()
    val inventory = if (size == 41) Bukkit.createInventory(null, InventoryType.PLAYER) else Bukkit.createInventory(null, size)

    inventory.contents.forEachIndexed { index, _ ->
        dataInput.readObject()?.let { inventory.setItem(index, it as ItemStack) }
    }

    dataInput.close()
    return inventory
}

/**
 * Calculates the number of empty slots in the inventory.
 *
 * @return The number of empty slots in the inventory.
 */
fun Inventory.emptySlots(): Int {
    return this.contents.count { it == null }
}

/**
 * Returns the total amount of a specific material present in the inventory.
 *
 * @param material The material to be counted.
 * @return The total amount of the given material in the inventory.
 */
fun Inventory.getAmountOf(material: Material): Int = filter { it?.type == material }.sumOf { it?.amount ?: 0 }

