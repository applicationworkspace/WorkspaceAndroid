package com.workspaceandroid.data.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val UTC = "UTC"

class TimeHelper: ITimeHelper {

    override fun getLongFromString(date: String?, pattern: String, timeZone: String?): Long {
        if (date == null) {
            return 0
        }
        val dateFormatFrom: DateFormat = SimpleDateFormat(pattern, Locale.US)
        dateFormatFrom.timeZone = TimeZone.getTimeZone(UTC)
        timeZone?.let {
            dateFormatFrom.timeZone = TimeZone.getTimeZone(it)
        }
        return try {
            dateFormatFrom.parse(date)?.time ?: 0
        } catch (e: Exception) {
            0
        }
    }

    override fun convertToFormattedTime(timeStamp: Long, format: String): String {
        return try {
            val dateFormat = SimpleDateFormat(format)
            val netDate = Date(timeStamp)
            dateFormat.format(netDate)
        } catch (e: Exception) {
            ""
        }
    }

    companion object {

        const val TIME_FORMAT__PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    }
}