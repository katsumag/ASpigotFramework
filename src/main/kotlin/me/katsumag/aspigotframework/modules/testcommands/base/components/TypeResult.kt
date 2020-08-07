package me.katsumag.aspigotframework.modules.testcommands.base.components

class TypeResult(val resolvedValue: Any?, val argumentName: String) {

    constructor(argumentName: Any) : this(null, argumentName.toString())

}