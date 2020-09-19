package me.katsumag.aspigotframework.testcommands.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Permission(val value: Array<String>)