package net.eknm.eknmlogistics.homeFlow

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.homeFlow.homeFragment.HomeFragment
import net.eknm.eknmlogistics.homeFlow.homeFragment.HomeViewModel
import net.eknm.eknmlogistics.orderFlow.OrderFlowFragment

class HomeFlowFragment : BaseFlowFragment<HomeFlowViewModel>() {
    override val vmClass = HomeFlowViewModel::class.java
    override fun initFlow() {
        showFragment(HomeFragment.newInstance())
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        when(childFragment) {
            is HomeFragment -> observeHomeViewModel(childFragment.viewModel)
        }
    }

    private fun observeHomeViewModel(homeViewModel: HomeViewModel) {
        homeViewModel.createOrderEvent.observe(viewLifecycleOwner, Observer {
            showFragment(OrderFlowFragment.newInstance())
        })
    }

    companion object {
        fun newInstance() = HomeFlowFragment()
    }
}