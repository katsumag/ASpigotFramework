package me.katsumag.aspigotframework.modules.testcommands.base

import me.katsumag.aspigotframework.colour
import me.katsumag.aspigotframework.modules.testcommands.annotations.*
import me.katsumag.aspigotframework.modules.testcommands.base.components.CommandData
import me.katsumag.aspigotframework.modules.testcommands.exceptions.CommandException
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.util.*

class CommandHandler internal constructor(// Handler for the parameter types
        private val parameterHandler: ParameterHandler, private val completionHandler: CompletionHandler,
        private val messageHandler: MessageHandler, private val command: CommandBase,
        commandName: String?, aliases: List<String?>, private var hideTab: Boolean) : Command(commandName!!) {
    // Contains all the sub commands
    private val commands = mutableMapOf<String, CommandData?>()


    init {
        addSubCommands(command)
    }

    fun addSubCommands(command: CommandBase) {
        // Iterates through all the methods in the class.
        for (method in command.javaClass.declaredMethods) {
            val subCommand = CommandData(command)

            // Checks if the method is public and if it is annotated by @Default or @SubCommand.
            if (!method.isAnnotationPresent(Default::class.java) && !method.isAnnotationPresent(SubCommand::class.java) || !Modifier.isPublic(method.modifiers)) continue

            // Checks if default method has no parameters.
            if (method.parameterCount == 0) throw CommandException("Method $method.name in class $command.javaClass.name needs to have Parameters!")

            // Checks if the fist parameter is either a player or a sender.
            if (!CommandSender::class.java.isAssignableFrom(method.parameterTypes[0])) throw CommandException("Method $method.name in class $command.javaClass.name - first parameter needs to be a CommandSender, Player, or ConsoleCommandSender!")

            // Starts the command data object.
            subCommand.method = method
            // Sets the first parameter as either player or command sender.
            subCommand.senderClass = method.parameterTypes[0]

            // Checks if the parameters in class are registered.
            checkRegisteredParams(method, command, subCommand)

            // Checks if it's a default method.
            checkDefault(method, subCommand)

            // Checks if permission annotation is present.
            checkPermission(method, subCommand)

            // Checks if wrong usage annotation is present.
            checkWrongUsage(method, subCommand)

            // Check if optional parameter is present.
            checkOptionalParam(method, command, subCommand)

            // Checks for completion annotation in the method.
            checkMethodCompletion(method, command, subCommand)

            // Checks for completion on the parameters.
            checkParamCompletion(method, command, subCommand)

            // Checks for aliases.
            checkAlias(method, subCommand)

            // puts the main method in the list.
            if (!subCommand.defaultCmd && method.isAnnotationPresent(SubCommand::class.java)) {
                val name: String = method.getAnnotation(SubCommand::class.java).value.toLowerCase()
                subCommand.name = name
                commands[name] = subCommand
            }

            // Puts a default command in the list.
            if (subCommand.defaultCmd) {
                subCommand.name = "default"
                commands["default"] = subCommand
            }

            // Checks for a completion method
            checkCompletionMethod(command, subCommand)
        }
    }

    override fun execute(sender: CommandSender, label: String, arguments: Array<String>): Boolean {
        var subCommand: CommandData? = defaultSubCommand
        if (arguments.isEmpty() || arguments[0].isEmpty()) {

            // Will not run if there is no default methods.
            if (subCommand == null) return unknownCommand(sender)

            // Checks if permission annotation is present.
            // Checks whether the command sender has the permission set in the annotation.
            if (subCommand.hasPermissions() && !hasPermissions(sender, subCommand)) {
                return noPermission(sender)
            }

            // Checks if the command can be accessed from console
            if (!(CommandSender::class.java == subCommand.senderClass || ConsoleCommandSender::class.java == subCommand.senderClass) && sender !is Player) {
                return noConsole(sender)
            }

            // Checks if the command can be accessed from console
            return if (ConsoleCommandSender::class.java == subCommand.senderClass && sender is Player) {
                noPlayer(sender)
            } else executeCommand(subCommand, sender, arguments)

            // Executes all the commands.
        }

        // Sets the command to lower case so it can be typed either way.
        val argCommand = arguments[0].toLowerCase()

        // Checks if the sub command is registered or not.
        if (subCommand != null && subCommand.params.size == 0 && (!commands.containsKey(argCommand) || name.equals(argCommand, ignoreCase = true))) {
            return unknownCommand(sender)
        }

        // Checks if the sub command is registered or not.
        if (subCommand == null && !commands.containsKey(argCommand)) return unknownCommand(sender)

        // Checks if the command is on the list, which means it's no longer a default command.
        if (commands.containsKey(argCommand)) subCommand = commands[argCommand]
        assert(subCommand != null)
        if (subCommand!!.hasPermissions() && !hasPermissions(sender, subCommand)) {
            return noPermission(sender)
        }

        // Checks if the command can be accessed from console
        if (!(CommandSender::class.java == subCommand.senderClass || ConsoleCommandSender::class.java == subCommand.senderClass) && sender !is Player) {
            return noConsole(sender)
        }

        // Checks if the command can be accessed from console
        return if (ConsoleCommandSender::class.java == subCommand.senderClass && sender is Player) {
            noPlayer(sender)
        } else executeCommand(subCommand, sender, arguments)


        // Runs the command executor.
    }

    private fun executeCommand(subCommand: CommandData, sender: CommandSender, arguments: Array<String>): Boolean {
        try {
            val method: Method? = subCommand.method

            // Checks if it the command is default and remove the sub command argument one if it is not.
            val argumentsList: MutableList<String?> = LinkedList(Arrays.asList(*arguments))
            if (!subCommand.defaultCmd && argumentsList.size > 0) argumentsList.removeAt(0)

            // Check if the method only has a sender as parameter.
            if (subCommand.params.size == 0 && argumentsList.size == 0) {
                method?.invoke(subCommand.commandBase, sender)
                return true
            }

            // Checks if it is a default type command with just sender and args.
            if (subCommand.params.size == 1
                    && Array<String>::class.java.isAssignableFrom(subCommand.params[0])) {
                method?.invoke(subCommand.commandBase, sender, arguments)
                return true
            }

            // Checks for correct command usage.
            if (subCommand.params.size != argumentsList.size && !subCommand.optional) {
                if (!subCommand.defaultCmd && subCommand.params.size == 0) return wrongUsage(sender, subCommand)
                if (!Array<String>::class.java.isAssignableFrom(subCommand.params[subCommand.params.size - 1])) return wrongUsage(sender, subCommand)
            }

            // Creates a list of the params to send.
            val invokeParams: MutableList<Any?> = ArrayList()
            // Adds the sender as one of the params.
            invokeParams.add(sender)

            // Iterates through all the parameters to check them.
            for (i in 0 until subCommand.params.size) {
                val parameter: Class<*> = subCommand.params[i]

                // Checks for optional paramWe eter.
                if (subCommand.optional) {
                    if (argumentsList.size > subCommand.params.size) return wrongUsage(sender, subCommand)
                    if (argumentsList.size < subCommand.params.size - 1) return wrongUsage(sender, subCommand)
                    if (argumentsList.size < subCommand.params.size) argumentsList.add(null)
                }

                // checks if the parameters and arguments are valid
                if (subCommand.params.size > argumentsList.size) return wrongUsage(sender, subCommand)
                var argument: Any? = argumentsList[i]

                // Checks if the current argument is annotated by @Values
                if (subCommand.valuesArgs.contains(i + 1)) {
                    val completionId: String? = subCommand.completions[i + 1]
                    // Checks if the value introduced is part of the completion
                    completionId?.let {
                        completionHandler.getTypeResult(it, it)?.let {
                            if (! it.contains(argument)) argument = null
                        }
                    }
                }

                // Checks for String[] args.
                if (parameter == Array<String>::class.java) {
                    val args = arrayOfNulls<String>(argumentsList.size - i)
                    for (j in args.indices) {
                        args[j] = argumentsList[i + j]
                    }
                    argument = args
                }
                val result = parameterHandler.getTypeResult(parameter, argument!!, subCommand, subCommand.parameterNames[i])
                invokeParams.add(result)
            }

            // Calls the command method method.
            method?.invoke(subCommand.commandBase, invokeParams.toTypedArray())
            subCommand.commandBase.clearArgs()
            return true
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return true
    }

    @Throws(IllegalArgumentException::class)
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): List<String> {
        // Checks if args is 1 so it sends the sub commands completion.
        if (args.size == 1) {
            var commandNames: MutableList<String> = ArrayList()
            val subCommand: CommandData? = defaultSubCommand
            val subCmd: MutableList<String> = ArrayList(commands.keys)
            subCmd.remove("default")

            // removes commands that the player can't access.
            for (subCmdName in commands.keys) {
                val subCmdData: CommandData? = commands[subCmdName]
                subCmdData?.let { if (hideTab && it.hasPermissions() && !hasPermissions(sender, subCmdData)) subCmd.remove(subCmdName) }
            }
            if (subCommand != null && subCommand.completions.isNotEmpty()) {
                var id: String? = subCommand.completions[1]
                var inputClss: Any = subCommand.params[0]

                // TODO range without thingy and also for double

                id?.let {
                    if (it.contains(":")) {
                        val values = it.split(":").toTypedArray()
                        id = values[0]
                        inputClss = values[1]
                    }
                }

                id?.let { completionHandler.getTypeResult(it, inputClss)?.let { subCmd.addAll(it) } }
            }

            // Checks if the typing command is empty.
            if ("" != args[0]) {
                for (commandName in subCmd) {
                    if (!commandName.toLowerCase().startsWith(args[0].toLowerCase())) continue
                    commandNames.add(commandName)
                }
            } else {
                commandNames = subCmd
            }

            // Sorts the sub commands by alphabetical order.
            commandNames.sort()

            // Returns default tab completion if empty.
            return if (commandNames.isEmpty()) super.tabComplete(sender, alias, args) else commandNames

            // The complete values.
        }
        val subCommandArg = args[0]

        // Checks if it contains the sub command; Should always be true.
        if (!commands.containsKey(subCommandArg)) return super.tabComplete(sender, alias, args)
        val subCommand: CommandData? = commands[subCommandArg]

        // removes completion from commands that the player can't access.
        if (subCommand != null) {
            if (hideTab && subCommand.hasPermissions() && !hasPermissions(sender, subCommand)) return super.tabComplete(sender, alias, args)
        }
        val completionMethod: Method? = subCommand?.completionMethod
        if (completionMethod != null) {
            try {
                val argsList: MutableList<String> = LinkedList(listOf(*args))
                argsList.remove(subCommandArg)
                return completionMethod.invoke(subCommand?.commandBase, argsList) as List<String>
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }

        // Checks if the completion list has the current args position.
        if (subCommand != null)
        if (!subCommand.completions.containsKey(args.size - 1)) return super.tabComplete(sender, alias, args)

        // Gets the current ID.
        var id: String? = subCommand!!.completions[args.size - 1]
        var completionList: MutableList<String> = ArrayList()
        var inputClss: Any = subCommand.params[args.size - 2]

        // TODO range without thingy and also for double
        if (id != null)
        if (id!!.contains(":")) {
            val values = id!!.split(":").toTypedArray()
            id = values[0]
            inputClss = values[1]
        }
        val current = args[args.size - 1]

        // Checks if the typing completion is empty.
        if ("" != current) {
            for (completion in id?.let { completionHandler.getTypeResult(it, inputClss) }!!) {
                if (!completion.toLowerCase().contains(current.toLowerCase())) continue
                completionList.add(completion)
            }
        } else if (id != null) {
            val result = completionHandler.getTypeResult(id!!, inputClss)
            result?.let { completionList = result.toMutableList() }

        }

        // Sorts the completion content by alphabetical order.
        completionList.sort()

        // The complete values.
        return completionList
    }

    /**
     * Sets whether you want to hide or not commands from tab completion if players don't have permission to use them.
     *
     * @param hideTab Hide or Not.
     */
    fun setHideTab(hideTab: Boolean) {
        this.hideTab = hideTab
    }

    /**
     * Gets the default method from the Command Data objects.
     *
     * @return The Command data of the default method if there is one.
     */
    private val defaultSubCommand: CommandData?
        private get() = commands.getOrDefault("default", null)

    /**
     * Checks if the method is default.
     *
     * @param method     The method to check.
     * @param subCommand The subCommand object with the data.
     */
    private fun checkDefault(method: Method, subCommand: CommandData) {
        // Checks if it is a default method.
        if (!method.isAnnotationPresent(Default::class.java)) return
        subCommand.defaultCmd = true
    }

    /**
     * Checks if the method has registered parameters or not.
     *
     * @param method     The method to check.
     * @param command    The commandBase object with the data.
     * @param subCommand The SubCommand object with the data.
     */
    private fun checkRegisteredParams(method: Method, command: CommandBase, subCommand: CommandData) {
        // Checks if the parameters in class are registered.
        for (i in 1 until method.parameterTypes.size) {
            val clss = method.parameterTypes[i]
            if (clss == Array<String>::class.java && i != method.parameterTypes.size - 1) {
                throw CommandException("Method $method.name in class $command.javaClass.name String[] args have to be the last parameter if wants to be used!")
            }
            if (!parameterHandler.isRegisteredType(clss)) {
                throw CommandException("Method $method.name in class $command.javaClass.name contains unregistered parameter types!")
            }
            subCommand.params.add(clss)
            subCommand.parameterNames.add(method.parameters[i].name)
        }
    }

    /**
     * Checks if the permission annotation is present.
     *
     * @param method     The method to check.
     * @param subCommand The commandBase object with the data.
     */
    private fun checkPermission(method: Method, subCommand: CommandData) {
        // Checks if permission annotation is present.
        if (!method.isAnnotationPresent(Permission::class.java)) return

        // Checks whether the command sender has the permission set in the annotation.
        for (permission in method.getAnnotation(Permission::class.java).value) {
            subCommand.permissions.add(permission)
        }
    }

    /**
     * Checks if the WrongUsage annotation is present.
     *
     * @param method     The method to check.
     * @param subCommand The commandBase object with the data.
     */
    private fun checkWrongUsage(method: Method, subCommand: CommandData) {
        // Checks if WrongUsage annotation is present.
        if (!method.isAnnotationPresent(WrongUsage::class.java)) return

        // Checks whether the command sender has the permission set in the annotation.
        subCommand.wrongUsage = method.getAnnotation(WrongUsage::class.java).value
    }

    /**
     * Checks if there is any completion on the parameters.
     *
     * @param method     The method to check.
     * @param command    The commandBase object with the data.
     * @param subCommand The SubCommand object with the data.
     */
    private fun checkParamCompletion(method: Method, command: CommandBase, subCommand: CommandData) {
        // Checks for completion on the parameters.
        for (i in method.parameters.indices) {
            val parameter = method.parameters[i]
            if (i == 0 && parameter.isAnnotationPresent(Completion::class.java)) throw CommandException("Method $method.name in class $command.javaClass.name - First parameter of a command method cannot have Completion/Values annotation!")
            val values: Array<String>
            values = if (parameter.isAnnotationPresent(Completion::class.java)) parameter.getAnnotation(Completion::class.java).value else if (parameter.isAnnotationPresent(Values::class.java)) {
                arrayOf(parameter.getAnnotation(Values::class.java).value)
            } else continue
            if (values.size != 1) throw CommandException("Method $method.name in class $command.javaClass.name - Parameter completion can only have one value!")
            if (!values[0].startsWith("#")) throw CommandException("Method $method.name in class $command.javaClass.name - The completion ID must start with #!")
            if (completionHandler.isNotRegistered(values[0])) throw CommandException("Method $method.name in class $command.javaClass.name - Unregistered completion ID $values[0] !")
            subCommand.completions[i] = values[0]
            if (parameter.isAnnotationPresent(Values::class.java)) subCommand.valuesArgs.add(i)
        }
    }

    /**
     * Checks if there is any completion on the method.
     *
     * @param method     The method to check.
     * @param command    The commandBase object with the data.
     * @param subCommand The SubCommand object with the data.
     */
    private fun checkMethodCompletion(method: Method, command: CommandBase, subCommand: CommandData) {
        // Checks for completion annotation in the method.
        if (!method.isAnnotationPresent(Completion::class.java)) return
        val completionValues: Array<String> = method.getAnnotation(Completion::class.java).value
        for (i in completionValues.indices) {
            val id = completionValues[i]
            if (!id.startsWith("#")) throw CommandException("Method $method.name in class $command.javaClass.name - The completion ID must start with #!")
            if (completionHandler.isNotRegistered(id)) throw CommandException("Method $method.name in class $command.javaClass.name - Unregistered completion ID'$id'!")
            subCommand.completions[i + 1] = id
        }
    }

    private fun checkCompletionMethod(command: CommandBase, subCommand: CommandData) {
        // Checks for completion annotation in the method.
        for (method in command.javaClass.declaredMethods) {
            // Checks for CompletionFor annotation
            if (!method.isAnnotationPresent(CompleteFor::class.java)) continue

            // All the checks to make sure the complete for method returns String List
            if (method.genericReturnType !is ParameterizedType) return
            val parametrizedReturnType = method.genericReturnType as ParameterizedType
            if (parametrizedReturnType.rawType !== MutableList::class.java) return
            if (parametrizedReturnType.actualTypeArguments.size != 1) return
            if (parametrizedReturnType.actualTypeArguments[0] !== String::class.java) return
            val subCommandName = method.getAnnotation(CompleteFor::class.java).value
            if (!subCommandName.equals(subCommand.name, ignoreCase = true)) continue
            subCommand.completionMethod = method
        }
    }

    /**
     * Checks for aliases to be used.
     *
     * @param method     The method to check.
     * @param subCommand The SubCommand object with the data.
     */
    private fun checkAlias(method: Method, subCommand: CommandData) {
        // Checks for aliases.
        if (!method.isAnnotationPresent(Alias::class.java)) return

        // Iterates through the alias and add each as a normal sub command.
        for (alias in method.getAnnotation(Alias::class.java).aliases) {
            val aliasCD: CommandData = subCommand
            subCommand.name = alias.toLowerCase()
            if (aliasCD.defaultCmd) aliasCD.defaultCmd = false
            commands[alias.toLowerCase()] = subCommand
        }
    }

    /**
     * Checks for optional parameter
     *
     * @param method     The method to check from
     * @param command    The command base class
     * @param subCommand The current sub command
     */
    private fun checkOptionalParam(method: Method, command: CommandBase, subCommand: CommandData) {
        // Checks for completion on the parameters.
        for (i in method.parameters.indices) {
            val parameter = method.parameters[i]
            if (i != method.parameters.size - 1 && parameter.isAnnotationPresent(me.katsumag.aspigotframework.modules.testcommands.annotations.Optional::class.java)) throw CommandException("Method $method.name in class $command.javaClass.name - Optional parameters can only be used as the last parameter of a method!")
            if (parameter.isAnnotationPresent(me.katsumag.aspigotframework.modules.testcommands.annotations.Optional::class.java)) subCommand.optional = true
        }
    }

    /**
     * Checks if the player has one of the permissions listed
     *
     * @param sender     The command sender
     * @param subCommand The sub command class to check
     * @return If has or not one of the permissions
     */
    private fun hasPermissions(sender: CommandSender, subCommand: CommandData?): Boolean {
        if (subCommand != null) {
            for (permission in subCommand.permissions) {
                if (sender.hasPermission(permission)) return true
            }
        }
        return false
    }

    /**
     * Sends the wrong message to the sender
     *
     * @param sender     The sender
     * @param subCommand The current sub command to get info from
     * @return Returns true
     */
    private fun wrongUsage(sender: CommandSender, subCommand: CommandData): Boolean {
        val wrongMessage: String = subCommand.wrongUsage
        if (!wrongMessage.startsWith("#") || !messageHandler.hasId(wrongMessage)) {
            messageHandler.sendMessage("cmd.wrong.usage", sender)
            sender.sendMessage(subCommand.wrongUsage.colour())
            return true
        }
        messageHandler.sendMessage(wrongMessage, sender)
        return true
    }

    /**
     * Sends the unknown message to the sender
     *
     * @param sender The sender
     * @return Returns true
     */
    private fun unknownCommand(sender: CommandSender): Boolean {
        messageHandler.sendMessage("cmd.no.exists", sender)
        return true
    }

    /**
     * Sends the no permission message to the sender
     *
     * @param sender The sender
     * @return Returns true
     */
    private fun noPermission(sender: CommandSender): Boolean {
        messageHandler.sendMessage("cmd.no.permission", sender)
        return true
    }

    /**
     * Sends the no console allowed message to the sender
     *
     * @param sender The sender
     * @return Returns true
     */
    private fun noConsole(sender: CommandSender): Boolean {
        messageHandler.sendMessage("cmd.no.console", sender)
        return true
    }

    /**
     * Sends the no console allowed message to the sender
     *
     * @param sender The sender
     * @return Returns true
     */
    private fun noPlayer(sender: CommandSender): Boolean {
        messageHandler.sendMessage("cmd.no.player", sender)
        return true
    }
}