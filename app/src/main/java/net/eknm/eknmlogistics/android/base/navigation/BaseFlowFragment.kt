package net.eknm.eknmlogistics.android.base.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.BackPressHandler
import net.eknm.eknmlogistics.android.singleThreadLazy
import net.eknm.eknmlogistics.home.HomePaddingManager
import javax.inject.Inject

abstract class BaseFlowFragment<VM : BaseFlowViewModel> : Fragment(), HasSupportFragmentInjector,
    BackPressHandler, DrawerManager by DrawerManagerImpl() {

    protected abstract fun initFlow()

    protected abstract val vmClass: Class<VM>

    protected open val layoutId: Int = R.layout.layout_flow

    protected val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    val viewModel by singleThreadLazy {
        ViewModelProviders.of(this, viewModelFactory)[vmClass]
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedInstanceState?.let { viewModel.onViewStateRestored(it) }
            ?: if (currentFragment == null) initFlow()

        viewModel.openDrawerEvent.observe(viewLifecycleOwner, Observer {
            openDrawer()
        })
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    protected fun finishFlow() {
        viewModel.finishFlow()
    }

    protected open fun navigateBack() {
        if (!childFragmentManager.popBackStackImmediate()) {
            finishFlow()
        }
    }

    protected fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .apply {
                if (currentFragment != null) {
                    setCustomAnimations(
                        R.anim.slide_from_right,
                        R.anim.slide_to_left,
                        R.anim.slide_from_left,
                        R.anim.slide_to_right
                    )
                    addToBackStack(null)
                }
            }
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun handleBackPress() {
        val backPressHandler = currentFragment as? BackPressHandler
        backPressHandler?.handleBackPress() ?: navigateBack()
    }

    protected fun onChildFlowFinished() {
        navigateBack()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        if (childFragment is DrawerManager) {
            childFragment.openDrawerEvent.observe(this, Observer {
                viewModel.openDrawer()
            })
        }
        if (childFragment is BaseFragment<*, *>) {
            subscribeToBackEvents(childFragment.viewModel)
        }
        if (childFragment is BaseFlowFragment<*>) {
            val childFlowVM = childFragment.viewModel
            childFlowVM.getOnFinishFlowEvent().observe(this, Observer {
                onChildFlowFinished()
            })
        }

        if (this is HomePaddingManager && childFragment is HomePaddingManager) {
            childFragment.bottomPadding.observe(viewLifecycleOwner, Observer {
                setBottomPadding(it)
            })
        }
    }

    private fun subscribeToBackEvents(viewModel: BaseFragmentViewModel) {
        viewModel.onNavigateBack.observe(this, Observer {
            navigateBack()
        })
    }

    protected open fun showFragmentFromBottom(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_from_bottom, 0, 0, R.anim.slide_to_bottom)
            .addToBackStack(null)
            .add(R.id.container, fragment)
            .commit()
    }
}