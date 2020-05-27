package net.eknm.eknmlogistics.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import net.eknm.eknmlogistics.BR
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.databinding.FragmentHomeFlowBinding
import net.eknm.eknmlogistics.home.homeFragment.HomeFragment
import net.eknm.eknmlogistics.home.homeFragment.HomeViewModel
import net.eknm.eknmlogistics.order.OrderFlowFragment

class HomeFlowFragment : BaseFlowFragment<HomeFlowViewModel>() {
    override val vmClass = HomeFlowViewModel::class.java
    override val layoutId = R.layout.fragment_home_flow
    override fun initFlow() {
        showFragment(HomeFragment.newInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentHomeFlowBinding>(
            inflater, layoutId, container, false
        )
            .apply {
                setVariable(BR.viewModel, viewModel)
                centerButton.setOnClickListener {
                    this@HomeFlowFragment.viewModel.centerLocation()
                }
            }
            .root
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        when (childFragment) {
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