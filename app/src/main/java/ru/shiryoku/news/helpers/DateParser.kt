package ru.shiryoku.news.helpers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun parseDate(date: String?): String? {
    var format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale("ru"))
    val newDate = date?.let { format.parse(it) }

    format = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("en"))
    val formattedDate = newDate?.let { format.format(it) }
    return formattedDate
}