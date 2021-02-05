package net.eknm.eknmlogistics.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.home.HomePaddingManager
import net.eknm.eknmlogistics.home.HomePaddingManagerImpl
import net.eknm.eknmlogistics.order.destinationFragment.DestinationFragment
import net.eknm.eknmlogistics.order.destinationFragment.DestinationViewModel
import net.eknm.eknmlogistics.order.orderOverviewFragment.OrderOverviewFragment

class OrderFlowFragment : BaseFlowFragment<OrderFlowViewModel>(),
    HomePaddingManager by HomePaddingManagerImpl() {
    override val vmClass = OrderFlowViewModel::class.java
    override fun initFlow() {
        showFragment(DestinationFragment.newInstance(getString(R.string.select_start_destination)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        when (childFragment) {
            is DestinationFragment -> observeDestinationsViewModel(childFragment.viewModel)
        }
    }

    private fun observeViewModel() {
        viewModel.showEndDestinationFragmentEvent.observe(viewLifecycleOwner, Observer {
            showFragment(DestinationFragment.newInstance(getString(R.string.select_end_destination)))
        })

        viewModel.showOrderOverviewFragment.observe(viewLifecycleOwner, Observer {
            showFragment(OrderOverviewFragment.newInstance())
        })
    }

    private fun observeDestinationsViewModel(destinationViewModel: DestinationViewModel) {
        destinationViewModel.destinationSelected.observe(this, Observer {
            viewModel.onNewLocation(it)
        })
    }

    companion object {
        fun newInstance() = OrderFlowFragment()
    }
}