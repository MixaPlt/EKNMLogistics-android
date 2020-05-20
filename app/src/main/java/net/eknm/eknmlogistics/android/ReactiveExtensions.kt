package net.eknm.eknmlogistics.android

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.ioToMain() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())