package me.katsumag.aspigotframework.modules.testcommands.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
annotation class Alias(val aliases: Array<String>)