package net.eknm.eknmlogistics.order.orderOverviewFragment

import android.os.Bundle
import android.view.View
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentOrderOverviewBinding
import net.eknm.eknmlogistics.home.HomePaddingManager
import net.eknm.eknmlogistics.home.HomePaddingManagerImpl

class OrderOverviewFragment : BaseFragment<OrderOverviewViewModel, FragmentOrderOverviewBinding>(),
    HomePaddingManager by HomePaddingManagerImpl() {
    override val layoutResId = R.layout.fragment_order_overview
    override val vmClass = OrderOverviewViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.post {
            setBottomPadding(binding.bottomSheet.height)
        }
    }

    companion object {
        fun newInstance(): OrderOverviewFragment {
            return OrderOverviewFragment()
        }
    }
}