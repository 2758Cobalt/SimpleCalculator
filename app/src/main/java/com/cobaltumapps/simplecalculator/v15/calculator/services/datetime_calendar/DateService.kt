package com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar

import java.util.Calendar

class DateService: CalendarService() {

    /** Возвращает класс даных о дате (год, месяц, день месяца) */
    fun getCalendarDate(unixTimeInMillis: Long?= null): CalendarDateModel {
        if (unixTimeInMillis != null) {
            calendarInstance.timeInMillis = unixTimeInMillis
        }
        // В противном случае дата устанавливается по-умолчанию
        return CalendarDateModel(
            calendarInstance.get(Calendar.YEAR),
            calendarInstance.get(Calendar.MONTH),
            calendarInstance.get(Calendar.DAY_OF_MONTH)
        )
    }

    /** Форматирует дату из unix времени в миллисекундах */
    fun formatDateFromUnixTime(unixTimeInMillis: Long): CalendarDateModel {
        val newCalendar = Calendar.getInstance()
        newCalendar.timeInMillis = unixTimeInMillis

        return CalendarDateModel(
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
    }
}