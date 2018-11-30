# Caves of Zircon

## Usage

You need to install *Java* (JDK) on your computer before you can build Caves of Zircon.
Alternatively you can use one of the releases [here](https://github.com/Hexworks/caves-of-zircon/releases) for
which a JRE will suffice.

```java
git clone https://github.com/Hexworks/caves-of-zircon.git
cd caves-of-zircon
./gradlew clean build
 java -jar build/libs/caves-of-zircon-2018.1.0-PREVIEW.jar
```

## Tutorial notes

- Explain why components are objects -> why immutability is good
- Explain `reified`
- Improve destructuring (property name is at end, custom fields at front)
- Explain extension functions over `Entity` and others and their purpose
- Events with `EventBus` (remove entity)
- Explain `data class`es (destructuring the event)
- Introduce databinding (cobalt)
- Add `sameLevelNeighbors` and explain the utility of extension functions
- Introduce tile variations
- Explain why using functions is better than variables (`FLOOR` TO `floor()` when `Variation` is added)
- Introduce extension functions on nullable types
