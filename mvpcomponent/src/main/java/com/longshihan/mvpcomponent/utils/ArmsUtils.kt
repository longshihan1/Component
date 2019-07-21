package com.longshihan.mvpcomponent.utils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.longshihan.mvpcomponent.base.App
import com.longshihan.mvpcomponent.di.component.AppComponent
import com.longshihan.mvpcomponent.strategy.imageloader.ImageLoader
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * Created by LONGHE001.
 * @time 2019/7/21 0021
 * @des 一些框架常用的工具
 * @function
 */

object ArmsUtils {

     var mToast: Toast?=null

    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Int {
        var scale: Float = getResources(context).displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获得资源
     */
    @JvmStatic
    fun getResources(context: Context): Resources {
        return context.resources
    }

    /**
     * 得到字符数组
     */
    @JvmStatic
    fun getStringArray(context: Context, id: Int): Array<String> {
        return getResources(context).getStringArray(id)
    }

    /**
     * pix转dip
     */
    @JvmStatic
    fun pix2dip(context: Context, pix: Int): Int {
        val densityDpi = getResources(context).displayMetrics.density
        return (pix / densityDpi + 0.5f).toInt()
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    @JvmStatic
    fun getDimens(context: Context, id: Int): Int {
        return getResources(context).getDimension(id).toInt()
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param dimenName
     * @return
     */
    @JvmStatic
    fun getDimens(context: Context, dimenName: String): Float {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.packageName))
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */
    @JvmStatic
    fun getString(context: Context, stringID: Int): String {
        return getResources(context).getString(stringID)
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */
    @JvmStatic
    fun getString(context: Context, strName: String): String {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.packageName))
    }
    @JvmStatic
    fun makeText(context: Context, string: String) {
        if (mToast==null){
            mToast= Toast.makeText(context,string,Toast.LENGTH_SHORT)
        }
        mToast!!.setText(string)
        mToast!!.show()
    }

    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    @JvmStatic
    fun getDrawablebyResource(context: Context, rID: Int): Drawable {
        return getResources(context).getDrawable(rID)
    }


    /**
     * 跳转界面 3
     *
     * @param activity
     * @param homeActivityClass
     */
    @JvmStatic
    fun startActivity(activity: Activity, homeActivityClass: Class<*>) {
        val intent = Intent(activity.applicationContext, homeActivityClass)
        activity.startActivity(intent)
    }

    /**
     * 跳转界面 4
     *
     * @param
     */
    @JvmStatic
    fun startActivity(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        return getResources(context).displayMetrics.widthPixels
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    @JvmStatic
    fun getScreenHeidth(context: Context): Int {
        return getResources(context).displayMetrics.heightPixels
    }


    /**
     * 获得颜色
     */
    @JvmStatic
    fun getColor(context: Context, rid: Int): Int {
        return getResources(context).getColor(rid)
    }

    /**
     * 获得颜色
     */
    @JvmStatic
    fun getColor(context: Context, colorName: String): Int {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.packageName))
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    @JvmStatic
    fun removeChild(view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            parent.removeView(view)
        }
    }
    @JvmStatic
    fun isEmpty(obj: Any?): Boolean {
        return obj == null
    }


    /**
     * MD5
     *
     * @param string
     * @return
     */
    @JvmStatic
    fun encodeToMD5(string: String): String {
        var hash = ByteArray(0)
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.toByteArray(charset("UTF-8")))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val hex = StringBuilder(hash.size * 2)
        for (b  in hash) {
            if ((b  and 0xFF.toByte())< 0x10) {
                hex.append("0")
            }
            hex.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
        }
        return hex.toString()
    }

    /**
     * 配置 recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    @JvmStatic
    fun configRecycleView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }
    @JvmStatic
    fun obtainAppComponentFromContext(activity: Activity): AppComponent {
        return obtainAppComponentFromContext(activity as Context)
    }
    @JvmStatic
    fun obtainAppComponentFromContext(fragment: Fragment): AppComponent {
        return obtainAppComponentFromContext(fragment.activity)
    }
    @JvmStatic
    fun obtainAppComponentFromContext(fragment: android.support.v4.app.Fragment): AppComponent {
        return obtainAppComponentFromContext(fragment.activity)
    }
    @JvmStatic
    fun obtainAppComponentFromContext(context: Context): AppComponent {
        Preconditions.checkState(context.applicationContext is App, "Application does not implements App")
        return (context.applicationContext as App).getAppComponent()
    }
    @JvmStatic
    fun getImageLoader(context: Context): ImageLoader {
        return obtainAppComponentFromContext(context).imageLoader()
    }
    @JvmStatic
    fun getImageLoader(activity: Activity): ImageLoader {
        return obtainAppComponentFromContext(activity).imageLoader()
    }
    @JvmStatic
    fun getImageLoader(fragment: Fragment): ImageLoader {
        return obtainAppComponentFromContext(fragment).imageLoader()
    }
    @JvmStatic
    fun getImageLoader(fragment: android.support.v4.app.Fragment): ImageLoader {
        return obtainAppComponentFromContext(fragment).imageLoader()
    }
}