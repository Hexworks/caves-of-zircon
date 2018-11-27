package ctest.entities

import ctest.components.Component

object RootEntity : BaseEntity() {
    override val components = listOf<Component>()
}
