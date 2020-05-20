package net.eknm.eknmlogistics.android

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.ioToIo() =
    subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Single<T>.ioToMain() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.ioToMain() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.ioToMain() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())