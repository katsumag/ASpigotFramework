# Usage - ItemBuilder module

ItemFactory has two constructors, one that accepts a Material and one that accepts an ItemStack.

It also allows for retrieving player heads.

Quick note: You don't have to translate the colour codes for the name or lore, we do that for you.
## Java

To obtain an ItemStack:

```java
ItemStack item = new ItemFactoryKt(Material.ACACIA_BOAT)
        .setAmount(16)
        .setData(2)
        .setDisplayName("&4&ltest")
        .setLore("&2&lThis is a test")
        .build();
```

And to retrieve a player head:

```java
ItemStack item = ItemFactoryKt\.getPlayerSkull(aPlayerUUID).build();
```

## Kotlin
```kotlin
Material.OAK_WOOD.toItemStack()
        .setItemAmount(64)
        .setDisplayName("&4&lTest!")
        .setLore("This", "is", "a", "&4&lTest!")
```

And to retrieve a player head:

```kotlin
val item = getPlayerHead(aPlayerUUID)
```