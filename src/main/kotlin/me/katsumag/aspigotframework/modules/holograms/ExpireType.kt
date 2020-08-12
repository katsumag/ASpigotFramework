package me.katsumag.aspigotframework.modules.holograms

enum class ExpireType(val time: Long) {

    NEVER(Long.MAX_VALUE),
    TIMED(1200)

}