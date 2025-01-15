package com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar

import java.util.Calendar

/** Сервис, для форматирования или получении времени
 * @author Stanislav Kolomoiets*/
class TimeService: CalendarService() {

    /** Возвращает класс даных о времени (год, месяц, день месяца) */
    fun getCalendarTime(unixTimeInMillis: Long?= null): CalendarTimeModel {
        if (unixTimeInMillis != null) {
            calendarInstance.timeInMillis = unixTimeInMillis
        }

        // В противном случае время устанавливается по-умолчанию
        return CalendarTimeModel(
            calendarInstance.get(Calendar.HOUR),
            calendarInstance.get(Calendar.MINUTE),
            calendarInstance.get(Calendar.SECOND)
        )
    }

    /** Форматирует время из unix времени в миллисекундах */
    fun formatTimeFromUnixTime(unixTimeInMillis: Long): CalendarTimeModel {
        val newCalendar = Calendar.getInstance()
        newCalendar.timeInMillis = unixTimeInMillis

        return CalendarTimeModel(
            newCalendar.get(Calendar.HOUR),
            newCalendar.get(Calendar.MINUTE),
            newCalendar.get(Calendar.SECOND)
        )
    }
}