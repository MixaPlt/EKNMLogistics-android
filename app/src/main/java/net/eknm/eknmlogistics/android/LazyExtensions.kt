package net.eknm.eknmlogistics.android

fun <T> singleThreadLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)