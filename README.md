> [!WARNING]
> This API is still in pre-release there will might be bugs. If you find any please create an issue

# **UndefinedAPI**

UndefinedAPI is a papermc api to make the life of developers easier. This is a multi use library from small util classes to a GUI manager.

# Imports

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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

```<repositories>
dependencies {
    implementation 'com.github.TheRedMagic:UndefinedAPI:Version'
}
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

    override fun tabComplete(sender: CommandSender, args: Array<out String>): CommandTabUtil {
        return CommandTabUtil(listOf(), 0)
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

    @Override
    public CommandTabUtil tabComplete(CommandSender sender, String[] args) {
        return new CommandTabUtil(new ArrayList<>(), 0);
    }
}
```

## **Menu/GUI**

> [!CAUTION]
> KOTLIN ONLY

The menu part of this api is a powerful tool to help you make and use GUIS.

Before you create or use a menus you need to put in onEnable `setup(JavaPlugin)` (See below)

```kotlin
override fun onEnable() {
    MenuManager.setup(this)
}
```

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

To be able to create a GUI you have to extend the `generateInventory` method where you need to return a `Inventory`. There is a method for this called create Inventory.
After you have created the Inventory you will be able to add buttons using the method `Inventory.addButton(Button)`. The button class needs to parameters `slot` and `consumer`
The `consumer` will run then the button is pressed. (See below)

```kotlin
class FunMenu: UndefinedMenu("FUN") {
    override fun generateInventory() = createInventory {

        addButton(Button(10){
            player?.sendMessage("Button clicked!")
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