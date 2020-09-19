package me.katsumag.aspigotframework.testcommands.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
annotation class CompleteFor(val value: String)