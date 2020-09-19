package me.katsumag.aspigotframework.testcommands.base.components

class TypeResult(val resolvedValue: Any?, val argumentName: String) {

    constructor(argumentName: Any) : this(null, argumentName.toString())

}