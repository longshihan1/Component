package com.longshihan.mvpcomponent.utils

import android.text.TextUtils
import android.util.Log


object LogUtils {

    private val isLog = true
    val DEFAULT_TAG = "Mvparms"

    fun debugInfo(tag: String, msg: String) {
        if (!isLog || TextUtils.isEmpty(msg)) return
        Log.d(tag, msg)

    }

    /**
     * author  hhj
     * TODO
     *
     * @param msg void
     */
    fun debugInfo(msg: String) {
        debugInfo(DEFAULT_TAG, msg)
    }

    fun warnInfo(tag: String, msg: String) {
        if (!isLog || TextUtils.isEmpty(msg)) return
        Log.w(tag, msg)

    }

    /**
     * author  hhj
     * TODO
     *
     * @param msg void
     */
    fun warnInfo(msg: String) {
        warnInfo(DEFAULT_TAG, msg)
    }

    /**
     * author  hhj
     * TODO 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
     * 所以这里使用自己分节的方式来输出足够长度的message
     *
     * @param tag
     * @param str void
     */

    fun debugLongInfo(tag: String, str: String) {
        var str = str
        if (!isLog) return
        str = str.trim { it <= ' ' }
        var index = 0
        val maxLength = 3500
        var sub: String
        while (index < str.length) {
            // java的字符不允许指定超过总的长度end
            if (str.length <= index + maxLength) {
                sub = str.substring(index)
            } else {
                sub = str.substring(index, index + maxLength)
            }

            index += maxLength
            Log.d(tag, sub.trim { it <= ' ' })
        }
    }

    /**
     * author  hhj
     * TODO
     *
     * @param str void
     */
    fun debugLongInfo(str: String) {
        debugLongInfo(DEFAULT_TAG, str)
    }

}
