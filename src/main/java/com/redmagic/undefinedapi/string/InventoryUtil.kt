package com.redmagic.undefinedapi.string

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Convert the current inventory to a string representation using Base64 encoding.
 * This allows for easy storage or transmission of the inventory data.
 *
 * @return The string representation of the inventory.
 */
fun Inventory.asString(): String{

    val outputStream = ByteArrayOutputStream()
    val dataOutput = BukkitObjectOutputStream(outputStream)

    dataOutput.writeInt(this.size)

    for (i in this.size downTo 0){
        dataOutput.writeObject(this.getItem(i))
    }

    dataOutput.close()
    return Base64Coder.encodeLines(outputStream.toByteArray())

}

fun ItemStack.asString(): String{
    val outputStream = ByteArrayOutputStream()
    val dataOutput = BukkitObjectOutputStream(outputStream)

    dataOutput.writeObject(this)
    dataOutput.close()
    return Base64Coder.encodeLines(outputStream.toByteArray())
}


fun String.asItemStack(): ItemStack{
    val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(this))
    val dataInput = BukkitObjectInputStream(inputStream)
    val itemStack = dataInput.readObject() as ItemStack
    dataInput.close()
    return itemStack
}


/**
 * This method creates an inventory from a base64 encoded string.
 *
 * @return The created inventory.
 */
fun String.asInventory(): Inventory{
    val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(this))
    val dataInput = BukkitObjectInputStream(inputStream)

    val inv = Bukkit.createInventory(null, dataInput.readInt())

    for (i in inv.size downTo 0){
        inv.setItem(i, dataInput.readObject() as ItemStack)
    }

    dataInput.close()

    return inv
}

