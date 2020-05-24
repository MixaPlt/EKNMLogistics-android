package net.eknm.eknmlogistics.orderFlow

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.orderFlow.destinationFragment.DestinationFragment
import net.eknm.eknmlogistics.orderFlow.destinationFragment.DestinationViewModel

class OrderFlowFragment : BaseFlowFragment<OrderFlowViewModel>() {
    override val vmClass = OrderFlowViewModel::class.java
    override fun initFlow() {
        showFragment(DestinationFragment.newInstance(getString(R.string.select_start_destination)))
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        when (childFragment) {
            is DestinationFragment -> observeDestinationsViewModel(childFragment.viewModel)
        }
    }

    private fun observeDestinationsViewModel(destinationViewModel: DestinationViewModel) {
        destinationViewModel.destinationSelected.observe(this, Observer {
            showFragment(DestinationFragment.newInstance(getString(R.string.select_end_destination)))
        })
    }

    companion object {
        fun newInstance() = OrderFlowFragment()
    }
}