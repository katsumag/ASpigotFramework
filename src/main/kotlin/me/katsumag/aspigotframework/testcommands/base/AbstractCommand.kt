package me.katsumag.aspigotframework.testcommands.base

import me.katsumag.aspigotframework.extensions.colour

abstract class AbstractCommand : CommandBase() {

    open val wrongUsage = "&4Wrong usage!".colour()
    open val description = "Default Command Desciption"
    private val args = mutableMapOf<String, String>()
    private val _aliases = mutableSetOf<String>()

}