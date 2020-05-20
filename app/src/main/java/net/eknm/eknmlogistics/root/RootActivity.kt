package net.eknm.eknmlogistics.root

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.layout_drawer.view.*
import net.eknm.eknmlogistics.BR
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentActivity
import net.eknm.eknmlogistics.authorization.LoginActivity
import net.eknm.eknmlogistics.databinding.ActivityRootBinding

class RootActivity : BaseFragmentActivity<RootViewModel>() {

    override val layoutId = R.layout.activity_root
    override val vmClass: Class<RootViewModel> = RootViewModel::class.java

    private lateinit var binding: ActivityRootBinding
    private lateinit var map: GoogleMap
    private val currentFragment get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setVariable(BR.viewModel, viewModel)

        observeViewModel()
        drawer.logOutBlock.setOnClickListener {
            viewModel.logOut()
        }

        (mapContainer as SupportMapFragment).getMapAsync { googleMap ->
            setupMap(googleMap)
        }
    }

    private fun setupMap(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false
    }

    private fun observeViewModel() {
        viewModel.onLoggedOutEvent.observe(this, Observer {
            startActivity(LoginActivity.newIntent(this))
            finish()
        })
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, RootActivity::class.java)
        }
    }
}
