package org.hexworks.cavesofzircon.attributes

import org.hexworks.amethyst.api.Attribute

/**
 * Contains the current experience level of an entity.
 */
data class Experience(var currentXP: Int,
                      var currentLevel: Int = 1) : Attribute
