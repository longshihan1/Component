package com.longshihan.mvpcomponent.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.view.WindowManager

/**
 * Created by LONGHE001.
 * @time 2019/7/21 0021
 * @des
 * @function
 */
object AppUtil {

    @JvmStatic
    fun getAppVersionName(context: Context): String {
        var pm: PackageManager = context.packageManager
        var pi: PackageInfo
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
            return pi.versionName
        } catch (e: Exception) {
            e.stackTrace
        }
        return ""
    }


    /**
     * 获取应用程序版本名称信息
     */
    @JvmStatic
    fun getVersionName(context: Context): String {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取app版本号
     */
    @JvmStatic
    fun getAppVersionCode(context: Context): Int {
        val pm = context.packageManager
        val pi: PackageInfo
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
            return pi.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }

    @JvmStatic
    fun getDeviceId(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.deviceId
    }

    @JvmStatic
    fun dp2px(paramContext: Context, paramFloat: Float): Int {
        val scale = paramContext.resources.displayMetrics.density
        return (0.5f + paramFloat * scale).toInt()
    }

    @JvmStatic
    fun getWindowWidth(paramContext: Context): Int {
        return getWindowManager(paramContext).defaultDisplay.width
    }

    @JvmStatic
    fun getWindowManager(paramContext: Context): WindowManager {
        return paramContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}
