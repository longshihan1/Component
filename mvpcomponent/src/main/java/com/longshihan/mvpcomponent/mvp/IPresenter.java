package com.longshihan.mvpcomponent.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;


/**
 * Created by longshihan on 2017/8/16.
 * P层的通用接口。集成LifecycleObserver，管理生命周期
 */

public interface IPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate( LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart( LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume( LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause( LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop( LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy( LifecycleOwner owner);

    /**
     * 当状态发生变化时
     * @param owner
     * @param event
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onLifecycleChanged(LifecycleOwner owner,Lifecycle.Event event);
}
