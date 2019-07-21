package com.longshihan.mvpcomponent.utils

/**
 * Created by LONGHE001.
 *
 * @time 2018/12/21 0021
 * @des
 * @function
 */

class FakeCrashLibrary private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        fun log(priority: Int, tag: String, message: String) {}

        fun logWarning(t: Throwable) {}

        fun logError(t: Throwable) {}
    }
}
