package me.katsumag.aspigotframework.cooldown

import java.util.concurrent.TimeUnit

private const val DAYS_IN_WEEK = 7
private const val DAYS_IN_MONTH = 30
private const val DAYS_IN_YEAR = 365

/**
 * Parses a String in a "time" format (eg 3m2s) and returns the actual time value in seconds
 */
fun String.parseTime(): TimeResult {
    var seconds = 0L
    val scanner = TimeScanner(
            this
                    .replace(" ", "")
                    .replace("and", "")
                    .replace(",", "")
                    .toLowerCase()
    )
    var next: Long
    while (scanner.hasNext()) {
        next = scanner.nextLong()
        seconds += when (scanner.nextString()) {
            "s", "sec", "secs", "second", "seconds" -> next
            "m", "min", "mins", "minute", "minutes" -> TimeUnit.MINUTES.toSeconds(next)
            "h", "hr", "hrs", "hour", "hours" -> TimeUnit.HOURS.toSeconds(next)
            "d", "dy", "dys", "day", "days" -> TimeUnit.DAYS.toSeconds(next)
            "w", "week", "weeks" -> TimeUnit.DAYS.toSeconds(next * DAYS_IN_WEEK)
            "mo", "mon", "mnth", "month", "months" -> TimeUnit.DAYS.toSeconds(next * DAYS_IN_MONTH)
            "y", "yr", "yrs", "year", "years" -> TimeUnit.DAYS.toSeconds(next * DAYS_IN_YEAR)
            else -> throw IllegalArgumentException()
        }
    }
    return TimeResult(seconds)
}

/**
 * Possibly over-engineering, but this allows us to keep a meaningful return value from [parseTime] while not basically copying [TimeUnit]
 */
inline class TimeResult(private val seconds: Long) {

    fun to(unit: TimeUnit): Long {
        return unit.convert(seconds, TimeUnit.SECONDS)
    }
}