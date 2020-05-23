package net.eknm.eknmlogistics.home

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val layoutResId = R.layout.fragment_home
    override val vmClass = HomeViewModel::class.java

    companion object {
        fun newInstance() = HomeFragment()
    }
}