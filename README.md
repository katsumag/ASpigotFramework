# ASpigotFramework

A framework library that simplifies interacting with the Spigot API.

Authors: katsumag, Conclure

![Version](https://img.shields.io/nexus/r/me.katsumag/a-spigot-framework?nexusVersion=3&server=https%3A%2F%2Frepo.katsumag.me%2F&style=plastic) ![License]((https://img.shields.io/github/license/katsumag/ASpigotFramework?style=plastic))

#Setup

You will need to shade and relocate ASpigotFramework to be able to use it at runtime.

**Gradle**
```gradle

plugins {
    id 'com.github.johnrengelman.shadow' version '6.0.0' //Or newer version
}

repositories {
    maven { url "https://repo.katsumag.me/repository/maven-releases/" }
}

dependencies {
    implementation "me.katsumag:a-spigot-framework:[VERSION]"
}

shadowJar {
    relocate 'me.katsumag.aspigotframework', '[YOUR PACKAGE].itemactionslib'
}
```

An example of `[YOUR PACKAGE]` could be `xyz.you.plugin`. If you want to use latest version
of ASpigotFramework, use version `1.0`. It should be replace `[VERSION]`.

# Usage

##Listener Module

**kotlin**

```kotlin
createListener<SomeEvent>(YourPluginInstance) {
    //your code here
}
```

That's all that is required. ASF will register your listener for you.

##ItemBuilder Module

**kotlin**

ASF has extension functions for Material and ItemStack so that they are easier to work with. You can find them all [here](https://github.com/katsumag/ASpigotFramework/blob/master/src/main/kotlin/me/katsumag/aspigotframework/modules/itembuilder/ItemExtensions.kt)

An example of turning a Material into an ItemStack with a custom name and lore is shown below.

```kotlin
Material.OAK_WOOD.toItemStack()
        .setItemAmount(64)
        .setDisplayName("&4&lTest!")
        .setLore("This", "is", "a", "&4&lTest!")
```
       
#General Extension Functions

Since this framework/library is written in kotlin, we wrote some general extension functions, which you can find [here](https://github.com/katsumag/ASpigotFramework/blob/master/src/main/kotlin/me/katsumag/aspigotframework/GeneralExtensions.kt)

# Contribute

If you want to contribute you may open a pull request 
[here](https://github.com/katsumag/ASpigotFramework/compare),
as we accept most of them.

# Support

If you have any questions or are in need of support, you may join HelpChat discord server, or my own. 
If you join HelpChat be sure to ping katsumag#7876 or Conclure#0001.
[HelpChat](https://helpch.at/discord)
[My Server](https://discord.gg/BmjaCn3)
