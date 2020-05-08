package net.eknm.eknmlogistics.android.base.navigation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected var isInitialized: Boolean = false
        private set

    fun onAttach() {
        if (!isInitialized) {
            onInitialize()
            isInitialized = true
        }
    }

    protected open fun onInitialize() {
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected inline fun executeDisposable(block: () -> Disposable) {
        compositeDisposable.add(block())
    }

    protected fun removeDisposable(disposable: Disposable?) {
        disposable?.let { compositeDisposable.remove(it) }
    }
}