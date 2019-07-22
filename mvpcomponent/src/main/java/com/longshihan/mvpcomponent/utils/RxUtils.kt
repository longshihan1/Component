package com.longshihan.mvpcomponent.utils


import com.longshihan.mvpcomponent.mvp.IView
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


object RxUtils {

    fun <T> applySchedulers(view: IView): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        view.showLoading()//显示进度条
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        view.hideLoading()//隐藏进度条
                    }.compose(RxUtils.bindToLifecycle(view)) as Observable<T>
        }
    }


    fun <T> bindToLifecycle(view: IView): LifecycleTransformer<T> {
        return if (view is LifecycleProvider<*>) {
            (view as LifecycleProvider<*>).bindToLifecycle()
        } else {
            throw IllegalArgumentException("view isn't LifecycleProvider")
        }

    }

}
