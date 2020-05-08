package net.eknm.eknmlogistics.android.base.navigation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.singleThreadLazy
import javax.inject.Inject

abstract class BaseFragmentActivity<VM : BaseViewModel> : DaggerAppCompatActivity(),
    HasSupportFragmentInjector {

    abstract val layoutId: Int

    abstract val vmClass: Class<VM>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>

    protected open val currentFlowFragment get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFlowFragment<*>

    protected val viewModel by singleThreadLazy {
        ViewModelProviders.of(this, viewModelFactory)[vmClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        savedInstanceState ?: init()
        viewModel.onAttach()
    }

    override fun onBackPressed() {
        currentFlowFragment?.handleBackPress()
    }

    protected abstract fun init()
}