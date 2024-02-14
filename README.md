> [!WARNING]
> This API is still in pre-release there will be bugs.

# **UndefinedAPI**

UndefinedAPI is a papermc api to make the life of develepers easier. This is a multi use libary from small util classes to a GUI manager.

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
    implementation 'com.github.TheRedMagic:UndefinedAPI:Tag'
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

