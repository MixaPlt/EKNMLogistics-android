package net.eknm.eknmlogistics.order.destinationFragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_destination.*
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentDestinationBinding
import net.eknm.eknmlogistics.home.HomePaddingManager
import net.eknm.eknmlogistics.home.HomePaddingManagerImpl

class DestinationFragment : BaseFragment<DestinationViewModel, FragmentDestinationBinding>(),
    HomePaddingManager by HomePaddingManagerImpl() {
    override val layoutResId = R.layout.fragment_destination
    override val vmClass = DestinationViewModel::class.java
    private val title get() = arguments!!.getString(KEY_TITLE)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        toolbar.text = title

        view.post {
            setBottomPadding(destination.height)
        }
    }

    private fun observeViewModel() {
        viewModel.addressText.observe(viewLifecycleOwner, Observer {
            destination.text = it
        })
    }

    companion object {
        private const val KEY_TITLE = "KEY_TITLE"
        fun newInstance(title: String): DestinationFragment {
            return DestinationFragment()
                .apply {
                    arguments = Bundle()
                        .apply {
                            putString(KEY_TITLE, title)
                        }
                }
        }
    }
}