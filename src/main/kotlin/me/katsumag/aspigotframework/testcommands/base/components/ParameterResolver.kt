package me.katsumag.aspigotframework.testcommands.base.components

fun interface ParameterResolver {

    fun resolve(argument: Any): TypeResult

}