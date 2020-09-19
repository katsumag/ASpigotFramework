# Hologram Module - Usage

To create a hologram, you first need to create [Hologram Data](/me/katsumag/aspigotframework/holograms/HologramData.kt)
For the expireType argument, use the length of time in ticks before the hologram should be removed, or 0 to stay permanently, or if you want to control that.

## Kotlin

```kotlin
val data = HologramData(someLocation, "Hologram text", 0)
val hologram = Hologram(data)
```

## Java

```java
HologramDataKt data = new HologramDatakt(someLocation, "Hologram text", 0)
HologramKt hologram = new HologramKt(data)
```

You can get the armour stand used for the hologram, or remove and set its name directly with the Hologram class.