# Cooldown Module - Usage

The cooldowns take their durations as a formatted String, eg: "1h 2m 5s" for 1 hour, 2 minutes and 5 seconds.

All methods applicable for the cooldown class are:

- addAction(), which takes a lambda expression, the code is ran once the cooldown expires
- start(), starts the cooldown and runs the actions synchronously when it expires
- startAsync(), starts the cooldown and runs the actions asynchronously when it expires
- pause(), pauses the cooldown
- pause(SomeTime), takes the time in ticks as a Long, pauses the cooldown for that length of time
- cancel(), cancels the cooldown

## Kotlin

To add a cooldown to a player, you can use
```kotlin
val cooldown = CooldownManager(YourPluginInstance).addCooldown(SomePlayer, "A time")
```

If you want to use a Cooldown for another purpose, you can instantiate the class directly, like this:
```kotlin
val cooldown = ooldown(YourPluginInstance, "A time")
```

## Java

To add a cooldown to a player, you can use
```java
Cooldown cooldown = new CooldownManagerKt(YourPluginInstance).addCooldown(SomePlayer, "A time");
```

If you want to use a Cooldown for another purpose, you can instantiate the class directly, like this:
```java
Cooldown cooldown = new CooldownKt(YourPluginInstance, "A time");
```