package net.eknm.eknmlogistics.root

import android.content.Context
import android.content.Intent
import android.os.Bundle
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentActivity

class RootActivity : BaseFragmentActivity<RootViewModel>() {

    override val layoutId = R.layout.activity_root
    override val vmClass: Class<RootViewModel> = RootViewModel::class.java
    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, RootActivity::class.java)
        }
    }
}
