> [!WARNING]
> This API is still in pre-release there might be bugs. If you find any please create an issue

# **UndefinedAPI** [![](https://jitpack.io/v/TheRedMagic/UndefinedAPI.svg)](https://jitpack.io/#TheRedMagic/UndefinedAPI)

UndefinedAPI is a papermc api to make the life of developers easier. This is a multi use library from small util classes to a GUI manager.

## Imports

Here are the import if you want to use the API your self. You will have to add them to your `pom.xml` or `build.gradle.kts`

### Maven:
```<repositories>
<repository>
  <id>undefinedapi-repo</id>
  <name>UndefinedAPI</name>
  <url>https://repo.undefinedcreation.com/repo</url>
</repository>
```

```<repositories>
<dependency>
  <groupId>com.redmagic</groupId>
  <artifactId>UndefinedAPI</artifactId>
  <version>0.4.06</version>
</dependency>
```

### Gradle:
```<repositories>
maven {
    name = "undefinedapiRepo"
    url = uri("https://repo.undefinedcreation.com/repo")
}
```

```<repositories>
implementation("com.redmagic:UndefinedAPI:0.4.06")
```

## Setup
To set up UndefinedAPI you will need to put this in onEnable.
```kotlin
UndefinedAPI(this)
```
After that you are ready to go.

## ItemBuilder
An ItemBuilder is a very powerful class create to make the creation of items easy and fast instead of using `ItemMeta`. You can use this by creating a new instance of the class and defining a `Material` or even a `ItemStack` to edit. (See below)

### Kotlin
```kotlin
val itemStack = ItemBuilder(Material.DIAMOND)
                .setName("Diamonds")
                .setName(Component.text("Diamonds"))
                .setLore(mutableListOf(Component.text("DIAMONDS")))
                .addLine(Component.text("NEW LINE"))
                .setAmount(32)
                .addEnchant(Enchantment.LUCK, 1)
                .setCustomModelData(32)
                .setUnbreakable(false)
                .setLocalizedName("DIAMONDS")
                .setSkullOwner(UUID.randomUUID())
                .build()
```
### Java

```java
import java.util.List;

ItemStack itemStack = new ItemBuilder(Material.DIAMOND)
        .setName("Diamonds")
        .setName(Component.text("Diamonds"))
        .setLore(List.of(Component.text("DIAMONDS")))
        .addLine(Component.text("NEW LINE"))
        .setAmount(32)
        .addEnchant(Enchantment.LUCK, 1)
        .setCustomModelData(32)
        .setUnbreakable(false)
        .setLocalizedName("DIAMONDS")
        .setSkullOwner(UUID.randomUUID())
        .build();
```

## Encoding
This API allows you to easily change objects to a string and back to be able to be saved.

### ItemStack
To change a `ItemStack` to a string you can do `asString`. Then if you what the `ItemStack` back you can do `asItemStack`. This will use `Base64` to encode it into a string and back (See below)

#### Kotlin
```kotlin
val encodedString = itemStack.asString()

val itemStack = encodedString.asItemStack()
```

#### Java
```java
String encodedString = itemStack.asString();

ItemStack itemStack = InventoryUtilKt.asItemStack(encodedString);
```

### Inventory
`Inventory` are the same as a `ItemStack` with the `asString` method and the `asInvenory` method. (See below)

#### Kotlin
```kotlin
val encodedInventory = inventory.asString()
        
val inventory = encodedInventory.asInventory()
```

#### Java
```java
String encodedInventory = inventory.asString();

Inventory inventory = InventoryUtilKt.asInventory(encodedInventory);
```

### Location
`Location` is not different from the rest. (See below)

#### Kotlin
```kotlin
val encodedLocation = location.asString()
        
val location = encodedLocation.asLocation()
```

#### Java

```java
String encodedLocation = location.asString();

Location location = InventoryUtilKt.asLocation(encodedLocation);
```
## Commands
To be able to create a command with UndefinedAPI you will need to extend the UndefinedCommand Class (See below)
The UndefinedCommand class has 5 parameter.

> [!NOTE]
> The Command Name var is also be the command name in game

``commandName``

The name of the command

> [!NOTE]
> Default is ALL

``commandType``

The commandType is an enum to be able to select for who the command to pointed for.

> [!NOTE]
> Optional

``description``

The description of the command

> [!NOTE]
> Optional

A list of all the aliases of the command you want

> [!NOTE]
> Optional

The permission to be able to run the command

### **Kotlin:**

``` kotlin
class FunCommand: UndefinedCommand("Test") {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        sender.sendMessage("FUN!!")
    }
}
```

### **Java**
```java
public class FunCommand extends UndefinedCommand {
    public FunCommand() {
        super("Test", CommandType.ALL, "A command", List.of(), "");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("FUN!!");
    }
}
```
## Tab Completion
To be able to tab complete with the UndefinedCommand you will need to extend the `tabComplete` method.
With this method you will be able to use it just like the default tab complete in spigot except you won't need to use `StringUtil` to be able to sort it. (See below)

### **Kotlin**
```kotlin
override fun tabComplete(sender: CommandSender, args: Array<out String>): CommandTabUtil {
        
    val nameList: MutableList<String> = mutableListOf()
        
    Bukkit.getOnlinePlayers().forEach{ nameList.add(it.name) }
        
    return CommandTabUtil(nameList, 0)
}
```

### **Java**
```java
@Override
public CommandTabUtil tabComplete(CommandSender sender, String[] args) {
    ArrayList<String> nameList = new ArrayList<>();

    Bukkit.getOnlinePlayers().forEach(player -> nameList.add(player.getName()));
        
    return new CommandTabUtil(nameList, 0);
}
```

## **Menu/GUI**

> [!CAUTION]
> KOTLIN ONLY

The menu part of this api is a powerful tool to help you make and use GUIS.

### Opening GUI
To be able to open a GUI you will need a GUI Object and a Player Object then you can use the `openMenu(Menu)` method (See below)

```kotlin
player.openMenu(SettingsGUI())
```

### Menu Creation

You can start creating one by extending the UndefinedMenu class. This class only has 2 parameters.

``title``

This is the title of the GUI

> [!NOTE]
> Optional. Default : LARGE
> If you what to manually choose the size you can use an int

``MenuSize``

This is an enum to be able to choose the size of the GUI

To be able to create a GUI you have to extend the `generateInventory` method where you need to return a `Inventory`. There is a method for this called `createInventory`. (See below)

```kotlin
class FunMenu: UndefinedMenu("FUN") {
    override fun generateInventory() = createInventory {
        
    }
}
```

#### Buttons
After you have created the Inventory you will be able to add buttons using the method `Inventory.addButton(Button)`. The button class needs to parameters `slot` and `consumer`
The `consumer` will run then the button is pressed. (See below)

```kotlin
class FunGUI: UndefinedMenu("Fun") {
    override fun generateInventory() = createInventory {

        addButton(Button(10){
            Bukkit.broadcastMessage("Button yeeeeee")
        })

    }
}
```

##### Menu Button
An menu button is a button when press it will change menus. (See below)

```kotlin
class FunGUI: UndefinedMenu("Fun") {
    override fun generateInventory() = createInventory {

        addButton(MenuButton(10, DifferntMenu()){
            Bukkit.broadcastMessage("Menu button")
        })

    }
}
```

#### Shared
A shared GUI is an gui that can be opened by multiple players, it will update for all players as wel.

##### Example
Lets say you have a minion GUI that you what to be shared. You can have a minion object and in that object have a var of your GUI. (See below)

**Main Class**
```kotlin
class Minion {
    val menu = MinionGUI()
}
```

**Menu Class**
```kotlin
class MinionGUI: UndefinedMenu("Minion") {
    override fun generateInventory() = createInventory { 
        //Creates GUI
    }
}
```

#### Per Player
A per player GUI is an GUI that is that the gui can be different for every player. You can do this by creating a new instance of the Menu class when opening it (See below)

**Opening**
```kotlin
player.openMenu(SettingsGUI())
```

**Menu Class**
```kotlin
class SettingsGUI: UndefinedMenu("Settings") {
    override fun generateInventory() = createInventory {
        //Creates GUI
    }
}
```

### Paged Menu

> [!CAUTION]
> KOTLIN ONLY

To be able to create a custom paged Menu you will need to extend the `UndefinedPageMenu`. Its never the same as a normal Menu but you will need to pase a `List` of itemStacks witch will be sorted into pages. When the same as the normal Menu you will to extend the `generateInventory` method with **`createPageInventory`** method.
After that put in your back button and next button using the `setBackButton` and `setNextButton`. You are able to shape the inventory to your liking. (See below)

> [!WARNING]
> You need to have the `setBackButton` and `setNextButton` for the gui to work.

> [!NOTE]
> Menu buttons still work with paged menus

```kotlin
class FunGui(list: List<ItemStack>): UndefinedPageMenu("Fun", MenuSize.LARGE, list) {
    override fun generateInventory(): Inventory = createPageInventory {

        setBackButton(PageButton(45, ItemStack(Material.RED_STAINED_GLASS_PANE), ItemStack(Material.GRAY_STAINED_GLASS_PANE)))
        setNextButton(PageButton(53, ItemStack(Material.LIME_STAINED_GLASS_PANE), ItemStack(Material.GRAY_STAINED_GLASS_PANE)))

        setColumn(6, ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName(" ").build())
    }
}
```

When lastly extend the `clickData: ClickData.()` witch will run then the gui is pressed. (See below)

```kotlin
override var clickData: ClickData.() -> Unit = {
    println("DIAMONDS")
}
```

## Events

> [!CAUTION]
> KOTLIN ONLY

When using this API you won't need to do and registering of event or even extending the `Listener` class. Even creating custom events is easier.

### Listening
Listening to an event is straight forward and easy. You need to create a method called `event<EventType>`. (See below)


```kotlin
event<PlayerJoinEvent> { 
    //Player Join
}
```

To be able to unregister the listener you can very easily put `.unregister` at the end. (See below)

```kotlin
event<PlayerJoinEvent> {
    //Player Join
}.unregister()
```

### Custom Event
Creating a custom event using this API you extend the `UndefinedEvent` class (See below)

```kotlin
class AsyncCustomEvent: UndefinedEvent(true) {
    //Async event
}

class SyncCustomEvent: UndefinedEvent() {
    //Sync event
}
```

## Scheduler

> [!CAUTION]
> KOTLIN ONLY

> [!Note]
> TimeUntil class was taken from [TwilightAPI](https://github.com/flytegg/twilight)

This API makes it easier to create tasks by make methods that be accessed any were. We are going to start by running code sync and async by using this. (See below)

```kotlin
sync { 
    //Run sync code
}
        
async { 
    //Run async code
}
```

### Delay

Next is delaying a task. This is not much different. (See below)

```kotlin
delay(20) {
    //This task will be run in 20 ticks
}

delay(20, true) {
    //This task will be run in 20 ticks async
}
        
delay(1, TimeUnit.SECONDS, false){
    //This task will be run is 20 ticks sync
}
```

### Repeating

Last one is creating repeating tasks. With this you will be able to give in how many times the task will run as wel. (See below)

```kotlin
repeatingTask(1) {
    //This will run every tick
}

repeatingTask(20, 5){
    //This will run every 20 ticks 5 times
}

repeatingTask(1, true){
    //This will run every tick async
}

repeatingTask(20, true, 5){
    //This will run every 20 ticks 5 times async
}

repeatingTask(1, TimeUnit.SECONDS){
    //This will run every second
}

repeatingTask(1, TimeUnit.SECONDS, 5){
    //This will run every second 5 times
}

repeatingTask(1, TimeUnit.SECONDS, true){
    //This will run every second async
}

repeatingTask(1, TimeUnit.SECONDS, 5, true){
    //This will run every second 5 times async
}
```

## Scoreboard
We have made it easy to create scoreboards when using this API. Instead of have to create teams and score in the spigot api. You can just create a `UndefinedScoreboard` class. After that you can create line with `addEmptyLine()`, `addLine(String)` and `addValueLine(Int, String, String)`. (See below)

### Kotlin
```kotlin
player.scoreboard = Bukkit.getScoreboardManager().newScoreboard

UndefinedScoreboard("Test", player.scoreboard)
    .addLine("IDK")
    .addEmptyLine()
    .addEmptyLine()
    .addValueLine(0, "Prefix ", " Suffix")
```

### Java
```java
ScoreboardManager manager = Bukkit.getScoreboardManager();
player.setScoreboard(manager.getNewScoreboard());

UndefinedScoreboard board = new UndefinedScoreboard("Test", player.getScoreboard());
board.addLine("IDK");
board.addEmptyLine();
board.addEmptyLine();
board.addValueLine(0, "Prefix ", " Suffix");
```

### Changing value line
To be able to change a value line you can use the `setValueLine(Int, String, String)`. The `Int` is the id of the line. (See below)

#### Kotlin
```kotlin
board.setValueLine(0, "Prefix2 ", " Suffix2")

board.setValueLine(0, prefix = "Prefix2 ")

board.setValueLine(0, suffix = " Suffix")
```

#### Java
```java
board.setValueLine(0, "Prefix2 ", " Suffix2");

board.setValueLine(0, "Prefix2 ", null);

board.setValueLine(0, null, " Suffix");
```

