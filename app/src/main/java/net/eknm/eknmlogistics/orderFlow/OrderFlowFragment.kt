package net.eknm.eknmlogistics.orderFlow

import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.orderFlow.destinationFragment.DestinationFragment

class OrderFlowFragment : BaseFlowFragment<OrderFlowViewModel>() {
    override val vmClass = OrderFlowViewModel::class.java
    override fun initFlow() {
        showFragment(DestinationFragment.newInstance())
    }

    companion object {
        fun newInstance() = OrderFlowFragment()
    }
}