package me.katsumag.aspigotframework.testcommands.base.components

fun interface CompletionResolver {

    fun resolve(input: Any): List<String>

}