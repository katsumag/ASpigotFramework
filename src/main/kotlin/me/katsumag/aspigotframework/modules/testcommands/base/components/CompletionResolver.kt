package me.katsumag.aspigotframework.modules.testcommands.base.components

fun interface CompletionResolver {

    fun resolve(input: Any): List<String>

}