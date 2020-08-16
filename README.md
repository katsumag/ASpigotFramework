# ASpigotFramework

A framework library that simplifies interacting with the Spigot API.

Authors: katsumag, Conclure

![Version](https://img.shields.io/nexus/r/me.katsumag/a-spigot-framework?nexusVersion=3&server=https%3A%2F%2Frepo.katsumag.me%2F&style=plastic) ![License](https://img.shields.io/github/license/katsumag/ASpigotFramework?style=plastic)

# Setup

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

 * [Listener Module](./src/main/resources/Listeners%20Usage.md)
 * [Cooldown Module](./src/main/resources/Cooldown%20Usage.md)
 * [WorldBuilder Module](./src/main/resources/WorldBuilder%20Usage.md)
 * [Hologram Module](./src/main/resources/Hologram%20Usage.md)
 * [ItemBuilder Module](./src/main/resources/ItemBuilder%20Usage.md)
       
# General Extension Functions

Since this framework/library is written in kotlin, we wrote some general extension functions, which you can find [here](src/main/kotlin/me/katsumag/aspigotframework/extensions)

# Contribute

If you want to contribute you may open a pull request 
[here](https://github.com/katsumag/ASpigotFramework/compare),
as we accept most of them.

# Support

If you have any questions or are in need of support, you may join HelpChat discord server, or my own. 

If you join HelpChat be sure to ping katsumag#7876 or Conclure#0001.

[HelpChat](https://helpch.at/discord)

[My Server](https://discord.gg/BmjaCn3)
