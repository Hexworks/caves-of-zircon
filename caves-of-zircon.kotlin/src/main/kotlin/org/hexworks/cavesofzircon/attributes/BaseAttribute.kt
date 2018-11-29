package org.hexworks.cavesofzircon.attributes

abstract class BaseAttribute : Attribute {

    override val name: String
        get() = this::class.simpleName!!
}
