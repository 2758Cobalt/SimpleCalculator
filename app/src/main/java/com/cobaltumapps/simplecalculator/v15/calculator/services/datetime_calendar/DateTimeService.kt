package com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateTimeService {
    private val calendarInstance by lazy { Calendar.getInstance() }

    /** Возвращает дату и время в формате паттера. Паттерн по-умолчанию:
     * EEEE - (день недели);
     * d MMMM - (день + полное назв. месяца);
     * yyyy - текущий год */
    private fun getStringFormatDateTime(pattern: String = "EEEE, d MMMM, yyyy"): String {
        return SimpleDateFormat(pattern, Locale.US).format(calendarInstance.time)
    }


    /** Возвращает класс даных о времени (год, месяц, день месяца) */
    fun getCalendarTime(): CalendarTimeModel {
        return CalendarTimeModel(
            calendarInstance.get(Calendar.HOUR),
            calendarInstance.get(Calendar.MINUTE),
            calendarInstance.get(Calendar.SECOND)
        )
    }

    /** Возвращает класс даных о дате (год, месяц, день месяца) */
    fun getCalendarDate(): CalendarDateModel {
        return CalendarDateModel(
            calendarInstance.get(Calendar.YEAR),
            calendarInstance.get(Calendar.MONTH),
            calendarInstance.get(Calendar.DAY_OF_MONTH)
        )
    }

    /** Возвращает число миллисекунд в unix (начиная с 1 января 1970 года) */
    fun getUnixTime(): Long {
        return calendarInstance.time.time
    }
}