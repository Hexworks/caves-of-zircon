package org.hexworks.cavesofzircon.attributes

enum class EntityType(val occupiesBlock: Boolean) {
    PLAYER(true),
    FUNGUS(true),
    WALL(true),
    STAIRS_DOWN(false),
    STAIRS_UP(false);
}
