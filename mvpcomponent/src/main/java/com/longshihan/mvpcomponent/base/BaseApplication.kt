package com.longshihan.mvpcomponent.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.longshihan.mvpcomponent.base.delegate.AppDelegate
import com.longshihan.mvpcomponent.base.delegate.AppLifecycles
import com.longshihan.mvpcomponent.di.component.AppComponent

/**
 * Created by LONGHE001.
 * @time 2019/7/21 0021
 * @des
 * @function
 */
open class BaseApplication : Application(), App {
    private var mAppDelegate: AppLifecycles? = null


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        if (mAppDelegate == null)
            this.mAppDelegate = AppDelegate(this)
        this.mAppDelegate!!.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate!!.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate!!.onTerminate(this)
    }

    override fun getAppComponent(): AppComponent = (mAppDelegate!! as App).getAppComponent()
}