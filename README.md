> [!WARNING]
> This API is still in pre-release there might be bugs. If you find any please create an issue

# **UndefinedAPI**

UndefinedAPI is a papermc api to make the life of developers easier. This is a multi use library from small util classes to a GUI manager.

## Imports

Maven:
```<repositories>
<repository>
    <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```<repositories>
<dependency>
    <groupId>com.github.TheRedMagic</groupId>
    <artifactId>UndefinedAPI</artifactId>
    <version>Version+</version>
</dependency>
```

Gradle:
```<repositories>
repositories {
    maven { url 'https://jitpack.io' }
}
```

```<repositories>
dependencies {
    implementation 'com.github.TheRedMagic:UndefinedAPI:Version'
}
```

## Setup
To set up UndefinedAPI you will need to put this in onEnable.
```kotlin
UndefinedAPI(this)
```
After that you are ready to go.

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
