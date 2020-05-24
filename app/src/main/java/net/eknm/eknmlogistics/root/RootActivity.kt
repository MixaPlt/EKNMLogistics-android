package net.eknm.eknmlogistics.root

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.subjects.SingleSubject
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.layout_drawer.view.*
import net.eknm.eknmlogistics.BR
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentActivity
import net.eknm.eknmlogistics.android.base.navigation.DrawerManager
import net.eknm.eknmlogistics.authorization.LoginActivity
import net.eknm.eknmlogistics.databinding.ActivityRootBinding
import net.eknm.eknmlogistics.homeFlow.HomeFlowFragment
import net.eknm.eknmlogistics.homeFlow.homeFragment.HomeFragment
import net.eknm.eknmlogistics.payments.PaymentsFlowFragment

class RootActivity : BaseFragmentActivity<RootViewModel>() {

    override val layoutId = R.layout.activity_root
    override val vmClass: Class<RootViewModel> = RootViewModel::class.java

    private lateinit var binding: ActivityRootBinding
    private lateinit var map: GoogleMap
    val mapSingle = SingleSubject.create<GoogleMap>()
    private val currentFragment get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun init() {
        viewModel.initFlow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setVariable(BR.viewModel, viewModel)

        observeViewModel()
        drawer.logOutBlock.setOnClickListener {
            viewModel.logOut()
        }

        checkPermissionsAndSetupMap()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkPermissionsAndSetupMap()
    }

    private fun checkPermissionsAndSetupMap() {
        if (checkPermissions()) {
            (mapContainer as SupportMapFragment).getMapAsync { googleMap ->
                setupMap(googleMap)
                mapSingle.onSuccess(googleMap)
            }
        }
    }

    private fun setupMap(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false
        map.isMyLocationEnabled = true
    }

    private fun observeViewModel() {
        viewModel.onLoggedOutEvent.observe(this, Observer {
            startActivity(LoginActivity.newIntent(this))
            finish()
        })

        viewModel.flowType.observe(this, Observer { flowType ->
            when (flowType!!) {
                FlowType.HOME -> showFragment(HomeFlowFragment.newInstance())
                FlowType.PAYMENTS -> showFragment(PaymentsFlowFragment.newInstance())
            }
            closeDrawer()
        })
    }

    private fun showFragment(
        fragment: Fragment,
        shouldAddToBackStack: Boolean = currentFragment != null
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .apply {
                if (shouldAddToBackStack) {
                    addToBackStack(null)
                }
            }
            .commit()
    }

    @SuppressLint("RtlHardcoded")
    private fun openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT, true)
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawers()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        if (childFragment is DrawerManager) {
            childFragment.openDrawerEvent.observe(this, Observer {
                openDrawer()
            })
        }
    }

    private fun checkPermissions(): Boolean {
        val neededPermissions = permissionsList.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }
        if (neededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                neededPermissions.toTypedArray(),
                RC_ACTIVITY_PERMISSIONS
            )
        }
        return neededPermissions.isEmpty()
    }

    companion object {
        private const val RC_ACTIVITY_PERMISSIONS = 1

        private val permissionsList = listOf(
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        )

        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, RootActivity::class.java)
        }
    }
}
