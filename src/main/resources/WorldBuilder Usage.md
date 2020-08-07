# WorldBuilder Module - Usage
## Kotlin

**Void Worlds**

```kotlin
val voidWorld = VoidWorldFactory("name").generate()
```

**Superflat Worlds**

**Default Superflat**

Default superflat will generate with the Y levels:

0, Bedrock

1, Stone

2, Dirt

3, Grass

```kotlin
val defaultSuperflatWorld = SuperflatWorldFactory(DefaultSuperflatOptions().options).generate("name")
```

**Custom Superflat**

You can specify your own superflat presets if you don't want to use the default. An example is shown below:

```kotlin
val customSuperflatWorld = SuperflatWorldFactory(LayerOptions(
        LayerOption(Material.BEDROCK).addY(0),
        LayerOption(Material.DIRT).addY(1, 2)
        LayerOption(Material.GRASS_BLOCK).addY(3, 4, 5),
)).generate("name")
```

It doesn't matter what order you create the LayerOptions in, however if you have conflicting Y levels, the one defined **last** will take priority.

## Java

**Void Worlds**

```java
World voidWorld = VoidWorldFactoryKt("name").generate()
```

**Superflat Worlds**

**Default Superflat**

Default superflat will generate with the Y levels:

0, Bedrock

1, Stone

2, Dirt

3, Grass

```java
World defaultSuperflatWorld = SuperflatWorldFactoryKt(DefaultSuperflatOptionsKt().options).generate("name")
```

**Custom Superflat**

You can specify your own superflat presets if you don't want to use the default. An example is shown below:

```java
World customSuperflatWorld = SuperflatWorldFactoryKt(LayerOptionsKt(
        LayerOptionKt(Material.BEDROCK).addY(0),
        LayerOptionKt(Material.DIRT).addY(1, 2)
        LayerOptionKt(Material.GRASS_BLOCK).addY(3, 4, 5),
)).generate("name")
```

It doesn't matter what order you create the LayerOptions in, however if you have conflicting Y levels, the one defined **last** will take priority.
