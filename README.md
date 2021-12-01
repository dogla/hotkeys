# hotkeys
This Java library for Windows allows the user to register global hot keys that can be triggered even when the associated application is not focused or has no application window at all.

# Dependencies

- Java 11
- `org.slf4j:slf4j-api:1.7.32`
- `net.java.dev.jna:jna-platform:5.10.0`

# Setup

Until now this library is not added to maven central. To use this library you have to build it on your own and install it to your local maven repo.
So a simple clone and build is all you need.

```
git clone https://github.com/dogla/hotkeys.git
cd hotkeys
mvnw clean install -DskipTests
```

After that you can use the library via a maven dependency:
```xml
    <dependency>
		<groupId>de.dogla</groupId>
		<artifactId>hotkeys</artifactId>
		<version>1.0.0-SNAPSHOT</version>
    <dependency>
```

If the snapshot version bothers you, you can also remove the snapshot and reinstall it again.
```
mvnw versions:set -DnewVersion=1.0.0
mvnw clean install -DskipTests
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
