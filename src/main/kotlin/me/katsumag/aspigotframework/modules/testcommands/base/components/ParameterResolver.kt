package me.katsumag.aspigotframework.modules.testcommands.base.components

fun interface ParameterResolver {

    fun resolve(argument: Any): TypeResult

}