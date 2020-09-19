package me.katsumag.aspigotframework.testcommands.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
annotation class Values(val value: String)