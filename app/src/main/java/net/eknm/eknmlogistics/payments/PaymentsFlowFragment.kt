package net.eknm.eknmlogistics.payments

import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.payments.paymentFragment.PaymentsFragment

class PaymentsFlowFragment : BaseFlowFragment<PaymentFlowViewModel>() {
    override val vmClass = PaymentFlowViewModel::class.java
    override fun initFlow() {
        showFragment(PaymentsFragment.newInstance())
    }

    companion object {
        fun newInstance() = PaymentsFlowFragment()
    }
}