package com.nakul.github_prs.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    private const val INITIAL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val TARGET_FORMAT = "dd MMM''yy h:mm a"

    @JvmStatic
    fun getFormattedDate(date: String?): String? = try {
        val parsingFormat = SimpleDateFormat(INITIAL_FORMAT, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val targetFormat = SimpleDateFormat(TARGET_FORMAT, Locale.getDefault()).apply{
            timeZone = TimeZone.getDefault()
        }
        targetFormat.format(parsingFormat.parse(date))
    } catch (exception: Exception) {
        Timber.w(exception)
        null
    }

}