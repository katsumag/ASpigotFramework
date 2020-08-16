package me.katsumag.aspigotframework.modules.testcommands.base

import me.katsumag.aspigotframework.extensions.colour
import me.katsumag.aspigotframework.modules.testcommands.base.components.CompletionResolver
import me.katsumag.aspigotframework.modules.testcommands.exceptions.CommandException
import me.katsumag.aspigotframework.extensions.sortCaseInsensitiveOrder
import org.bukkit.Bukkit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream


class CompletionHandler {

    private val registeredCompletions = mutableMapOf<String, CompletionResolver>()

    init {
        register("#players") { Bukkit.getOnlinePlayers().map { it.name }.toMutableList().sortCaseInsensitiveOrder() }
        register("#empty") { Collections.singletonList("") }
        register("#range") {
            val s = it.toString()
            if (s.contains("class")) IntStream.rangeClosed(1, 10).mapToObj { it.toString() }.collect(Collectors.toList())
            if (!s.contains("-")) IntStream.rangeClosed(1, s.toInt()).mapToObj { it.toString() }.collect(Collectors.toList())
            val minMax = s.split("-")
            IntStream.rangeClosed(minMax[0].toInt(), minMax[1].toInt()).mapToObj { it.toString() }.collect(Collectors.toList())
        }
        register("#enum") { (it as Class<out Enum<*>>).enumConstants.toMutableList().map { it.name }.toMutableList().sortCaseInsensitiveOrder() }
    }

    fun register(id: String, resolver: CompletionResolver) {
        if (! id.startsWith("#")) throw CommandException("Could not register completion, id -$id foes not start with #.")
        registeredCompletions[id] = resolver
    }

    fun getTypeResult(id: String, input: Any) = registeredCompletions[id]?.resolve(input)?.colour()

    fun isNotRegistered(id: String): Boolean = registeredCompletions[if (id.contains(":")) id.split(":")[0] else id] == null

}