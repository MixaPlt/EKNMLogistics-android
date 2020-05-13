package net.eknm.eknmlogistics.android.base.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import net.eknm.eknmlogistics.BR
import net.eknm.eknmlogistics.android.BackPressHandler
import net.eknm.eknmlogistics.android.showToast
import net.eknm.eknmlogistics.android.singleThreadLazy
import javax.inject.Inject

abstract class BaseFragment<VM : BaseFragmentViewModel, DB : ViewDataBinding> : Fragment(),
    BackPressHandler, DrawerManager by DrawerManagerImpl() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>

    protected abstract val vmClass: Class<VM>
    protected abstract val layoutResId: Int

    val viewModel by singleThreadLazy {
        ViewModelProviders.of(this, viewModelFactory)[vmClass]
    }

    private var nullableBindingProperty: DB? = null

    protected val binding get() = nullableBindingProperty!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel.onAttach()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBindingProperty = DataBindingUtil.inflate(
            inflater, layoutResId, container, false
        )

        binding.setVariable(BR.viewModel, viewModel)

        viewModel.showToastEvent.observe(viewLifecycleOwner, Observer {
            context!!.showToast(it)
        })

        viewModel.showStringToastEvent.observe(viewLifecycleOwner, Observer {
            context!!.showToast(it)
        })
        viewModel.openDrawerEvent.observe(viewLifecycleOwner, Observer {
            openDrawer()
        })

        viewModel.onNavigateBack.observe(viewLifecycleOwner, Observer {
            activity?.onBackPressed()
        })
        return binding.root
    }

    override fun handleBackPress() {
        viewModel.handleBackPress()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            viewModel.onViewStateRestored(savedInstanceState)
        }
    }

    fun showToast(@StringRes textRes: Int) = context!!.showToast(textRes)

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.onSaveInstanceState(outState)
    }
}