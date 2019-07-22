package com.longshihan.mvpcomponent.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/7/21
 * owspace
 */
object TimeUtil {
    val currentSeconds: Long
        get() = System.currentTimeMillis() / 1000

    fun getCalendarShowTime(paramLong: Long): Array<String> {
        val localObject: Array<String>?
        val str = SimpleDateFormat("yyyy:MMM:d", Locale.ENGLISH).format(Date(paramLong))
        try {
            val arrayOfString = str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            localObject = arrayOfString
            if (localObject != null && localObject.size == 3);
            return localObject
        } catch (localException: Exception) {
            while (true)
                localException.printStackTrace()
        }

    }

    fun getCalendarShowTime(paramString: String): Array<String>? {
        try {
            val l = java.lang.Long.valueOf(paramString)
            val localCalendar = Calendar.getInstance()
            localCalendar.timeInMillis = 1000L * l
            return getCalendarShowTime(localCalendar.timeInMillis)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return null
    }

    fun getDate(formate: String): String {
        return SimpleDateFormat(formate, Locale.ENGLISH).format(Date())
    }
}
