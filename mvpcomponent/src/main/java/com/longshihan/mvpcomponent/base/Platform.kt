package com.longshihan.mvpcomponent.base

/**
 * Created by LONGHE001.
 * @time 2019/7/21 0021
 * @des
 * @function
 */
object Platform {

    const val SS="ssss"
    val DEPENDENCY_AUTO_LAYOUT: Boolean
    val DEPENDENCY_SUPPORT_DESIGN: Boolean
    val DEPENDENCY_GLIDE: Boolean
    val DEPENDENCY_ANDROID_EVENTBUS: Boolean
    val DEPENDENCY_EVENTBUS: Boolean

    init {
        DEPENDENCY_AUTO_LAYOUT = findClassByClassName("com.zhy.autolayout.AutoLayoutInfo")
        DEPENDENCY_SUPPORT_DESIGN = findClassByClassName("com.zhy.autolayout.AutoLayoutInfo")
        DEPENDENCY_GLIDE = findClassByClassName("com.bumptech.glide.Glide")
        DEPENDENCY_ANDROID_EVENTBUS = findClassByClassName("org.simple.eventbus.EventBus")
        DEPENDENCY_EVENTBUS = findClassByClassName("org.greenrobot.eventbus.EventBus")
    }

    fun findClassByClassName(className: String): Boolean {
        return try {
            Class.forName(className)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

}