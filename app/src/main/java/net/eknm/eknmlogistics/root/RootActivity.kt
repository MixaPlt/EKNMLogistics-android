package net.eknm.eknmlogistics.root

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.layout_drawer.view.*
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentActivity
import net.eknm.eknmlogistics.authorization.LoginActivity

class RootActivity : BaseFragmentActivity<RootViewModel>() {

    override val layoutId = R.layout.activity_root
    override val vmClass: Class<RootViewModel> = RootViewModel::class.java
    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        observeViewModel()
        drawer.logOutBlock.setOnClickListener {
            viewModel.logOut()
        }
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
