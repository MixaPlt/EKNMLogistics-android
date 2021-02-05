package net.eknm.eknmlogistics.home.homeFragment

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_home.*
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentHomeBinding
import net.eknm.eknmlogistics.home.HomePaddingManager
import net.eknm.eknmlogistics.home.HomePaddingManagerImpl

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    HomePaddingManager by HomePaddingManagerImpl() {
    override val layoutResId = R.layout.fragment_home
    override val vmClass = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.post {
            setBottomPadding(createOrderButton.height)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}