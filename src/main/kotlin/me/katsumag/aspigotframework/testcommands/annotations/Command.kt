package me.katsumag.aspigotframework.testcommands.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
annotation class Command(val name: String)