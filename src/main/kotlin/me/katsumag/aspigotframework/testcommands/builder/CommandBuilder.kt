package me.katsumag.aspigotframework.testcommands.builder

class CommandBuilder(private val name: String) {

    fun addAlias(vararg alias: String): CommandBuilder {
        return this
    }

}