package com.example.thirdlibrary.util

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created by zyx on 2019/8/23.
 */

// rx相关
fun <T> Observable<T>.request(onNext: (T) -> Unit): Disposable = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(onNext, {
        if (it is HttpException) {
            //it.printStackTrace()
        }
        it.printStackTrace()
    })

fun <T> Observable<T>.subscribeWithError(onNext: (T) -> Unit): Disposable =
    this.subscribe(onNext, { it.printStackTrace() })

fun <T> Single<T>.request(onNext: (T) -> Unit): Disposable = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(onNext, {
        if (it is HttpException) {
            //it.printStackTrace()
        }
        it.printStackTrace()
    })

fun <T> Single<T>.subscribeWithError(onNext: (T) -> Unit): Disposable =
    this.subscribe(onNext, { it.printStackTrace() })
