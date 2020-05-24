package net.eknm.eknmlogistics.orderFlow.destinationFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentDestinationBinding

class DestinationFragment : BaseFragment<DestinationViewModel, FragmentDestinationBinding>() {
    override val layoutResId = R.layout.fragment_destination
    override val vmClass = DestinationViewModel::class.java

    companion object {
        fun newInstance() = DestinationFragment()
    }
}