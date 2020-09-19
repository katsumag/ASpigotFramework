package me.katsumag.aspigotframework.testcommands.base.components

import me.katsumag.aspigotframework.testcommands.base.CommandBase
import java.lang.reflect.Method

class CommandData(val commandBase: CommandBase) {

    var method: Method? = null
    var name: String? = null
    var defaultCmd: Boolean = false
    var senderClass: Class<*>? = null
    var completionMethod: Method? = null
    var optional: Boolean = false
    var wrongUsage = ""

    val params = mutableListOf<Class<*>>()
    val parameterNames = mutableListOf<String>()
    val permissions = mutableListOf<String>()
    val valuesArgs = mutableListOf<Int>()
    val completions = mutableMapOf<Int, String>()

    fun hasPermissions() = permissions.isNotEmpty()

}