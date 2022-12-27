
[![Maven Central](https://img.shields.io/maven-central/v/io.github.dogla/filesystem-watcher.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.dogla/hotkeys)

# hotkeys

This Java library for Windows allows the user to register global hot keys that can be triggered even when the associated application is not focused or has no application window at all.

# Dependencies

- Java 11
- `org.slf4j:slf4j-api:1.7.32`
- `net.java.dev.jna:jna-platform:5.10.0`

# Setup

To use this library you can use the corresponding maven dependency:

```xml
    <dependency>
		<groupId>io.github.dogla</groupId>
		<artifactId>hotkeys</artifactId>
		<version>1.0.0</version>
    <dependency>
```

# Usage

## Register hot keys

This sample creates a hot key (`addHotKey(id, modifiers, keyCode, handler)`) for the key sequence `ALT`+`CTRL`+`S` and prints the corresponding hot key to the console if the user presses this key combination.

Note: The registration can only work if no other application has already registered a hot key with the same key sequence.

```java
HotKeyManager.getInstance().addHotKey("myId", HotKey.MOD_CONTROL | HotKey.MOD_ALT, 'S', (event) -> {
    System.out.println("HotKey detected: " + event.getHotKey());
});
```

## Unregister hot keys

If the hot key is no more needed you can unregister the desired hot key with its `id`. The hot key is also unregistered if the application is closed.
```java
HotKeyManager.getInstance().removeHotKey("myId");
```
