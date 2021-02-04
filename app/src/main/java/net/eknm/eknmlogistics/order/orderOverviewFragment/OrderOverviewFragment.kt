package net.eknm.eknmlogistics.order.orderOverviewFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentOrderOverviewBinding

class OrderOverviewFragment : BaseFragment<OrderOverviewViewModel, FragmentOrderOverviewBinding>() {
    override val layoutResId = R.layout.fragment_order_overview
    override val vmClass = OrderOverviewViewModel::class.java

    companion object {
        fun newInstance(): OrderOverviewFragment {
            return OrderOverviewFragment()
        }
    }
}