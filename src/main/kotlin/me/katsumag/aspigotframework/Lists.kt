package me.katsumag.aspigotframework

fun List<String>.colour() : List<String> {
    return map { it.colour() }
}

fun MutableList<String>.sortCaseInsensitiveOrder(): MutableList<String> {
    sortWith(String.CASE_INSENSITIVE_ORDER)
    return this
}