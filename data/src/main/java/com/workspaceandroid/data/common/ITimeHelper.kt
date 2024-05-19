package com.workspaceandroid.data.common


const val TIME_FORMAT_MONTH = "MMM"
const val TIME_FORMAT_DAY_OF_THE_WEEK_PATTERN = "E"
const val TIME_FORMAT_TIME_PATTERN = "HH:mm"
const val TIME_FORMAT_LONG_TIME_PATTERN = "HH:mm:ss"
const val TIME_FORMAT_DATE_PATTERN = "dd/MM/yyyy"
const val TIME_FORMAT_DATE_BIRTH_PATTERN = "yyyy-MM-dd"
const val TIME_FORMAT_FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm"

const val TIME_FORMAT_RESPONSE_UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"


interface ITimeHelper {

    fun getLongFromString(date: String?, pattern: String, timeZone: String? = null): Long
    fun convertToFormattedTime(timeStamp:Long, format: String): String
}