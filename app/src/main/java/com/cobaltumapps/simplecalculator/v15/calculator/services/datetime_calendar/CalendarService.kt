package com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Master class for control and formatting of the java.util.Calendar.
 * @author Stanislav Kolomoiets */
open class CalendarService {
    protected val calendarInstance by lazy { Calendar.getInstance() }

    open fun setCalendarTimeUnix(unixTimeInMillis: Long) {
        calendarInstance.timeInMillis = unixTimeInMillis
    }

    /** Возвращает дату и время в формате паттера. Паттерн по-умолчанию:
     * EEEE - (день недели);
     * d MMMM - (день + полное назв. месяца);
     * yyyy - текущий год */
    open fun getStringFormatDate(unixTimestamp: Long, pattern: String = "EEEE, d MMMM, yyyy"): String {
        return SimpleDateFormat(pattern, Locale.US).format(unixTimestamp)
    }

    /** Возвращает число миллисекунд в unix (начиная с 1 января 1970 года) */
    open fun getUnixTime(): Long {
        return calendarInstance.time.time
    }
}

