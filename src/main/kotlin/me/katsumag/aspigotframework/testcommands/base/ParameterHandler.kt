package me.katsumag.aspigotframework.testcommands.base

import com.google.common.primitives.Ints
import me.katsumag.aspigotframework.testcommands.base.components.CommandData
import me.katsumag.aspigotframework.testcommands.base.components.ParameterResolver
import me.katsumag.aspigotframework.testcommands.base.components.TypeResult
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

class ParameterHandler {

    private val registeredTypes = mutableMapOf<Class<*>, ParameterResolver>()

    init {
        register(Short::class.java) { if (it.toString().toIntOrNull() == null) TypeResult(it) else TypeResult(it.toString().toShort(), it.toString()) }
        register(Integer::class.java) { TypeResult(it.toString().toIntOrNull(), it.toString()) }
        register(Long::class.java) { TypeResult(it.toString().toLongOrNull(), it.toString()) }
        register(Float::class.java) { TypeResult(it.toString().toFloatOrNull(), it.toString()) }
        register(Double::class.java) { TypeResult(it.toString().toDoubleOrNull(), it.toString()) }
        register(String::class.java) { if (it is String) TypeResult(it, it) else TypeResult(it) }
        register(Array<String>::class.java) { if (it is Array<*>) TypeResult(it, it.toString()) else TypeResult(it) }
        register(Boolean::class.java) { TypeResult(it.toString().toBoolean(), it.toString()) }
        register(Player::class.java) { TypeResult(Bukkit.getPlayer(it.toString()), it.toString()) }
        register(Material::class.java) { TypeResult(Material.matchMaterial(it.toString()), it.toString()) }
        register(Sound::class.java) {
            val sound = Arrays.stream(Sound.values())
                    .map(Enum<*>::name)
                    .filter { it.toLowerCase() == it.toString().toLowerCase() }
                    .findFirst().orElse(null)
            if (sound == null) TypeResult(null, it.toString()) else TypeResult(Sound.valueOf(sound.toString()), it.toString())
        }
        register(World::class.java) { TypeResult(Bukkit.getWorld(it.toString()), it.toString()) }
    }

    fun register(clazz: Class<*>, parameterResolver: ParameterResolver) = registeredTypes.put(clazz, parameterResolver)

    fun getTypeResult(clazz: Class<*>, objectt: Any, subCommand: CommandData, paramName: String): Any? {
        val result = registeredTypes[clazz]?.resolve(objectt)
        result?.let { subCommand.commandBase.addArgument(paramName, it.argumentName) }
        return result?.resolvedValue
    }

    fun isRegisteredType(clazz: Class<*>) = registeredTypes[clazz] != null

}