package net.eknm.eknmlogistics.payments.paymentFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentPaymentsBinding

class PaymentsFragment : BaseFragment<PaymentsViewModel, FragmentPaymentsBinding>() {
    override val vmClass = PaymentsViewModel::class.java
    override val layoutResId = R.layout.fragment_payments

    companion object {
        fun newInstance() = PaymentsFragment()
    }
}