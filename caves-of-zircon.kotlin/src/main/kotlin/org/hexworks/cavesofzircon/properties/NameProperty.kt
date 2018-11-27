package org.hexworks.cavesofzircon.properties

data class NameProperty(override val name: String) : Property {
    override fun toString() = name
}
