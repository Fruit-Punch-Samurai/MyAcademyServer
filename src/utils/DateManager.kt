package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateManager {

    fun getCurrentLocalDate() = Clock.System.now().toLocalDateTime(TimeZone.UTC)

}