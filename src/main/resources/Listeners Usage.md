# Usage - Listeners module

We automatically register the event listeners, this is all the code you'll need.

## Java

```java
RegisteredListenerKt.createListener<SomeEvent>(YourPluginInstance, event -> {
    //your code here
});
```
## Kotlin

```kotlin
createListener<SomeEvent>(YourPluginInstance) {
    //your code here
}
```
