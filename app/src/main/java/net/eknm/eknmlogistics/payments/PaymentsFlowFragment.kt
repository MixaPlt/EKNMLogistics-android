package net.eknm.eknmlogistics.payments

import android.view.LayoutInflater
import android.view.ViewGroup
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.databinding.LayoutFlowBinding
import net.eknm.eknmlogistics.payments.paymentFragment.PaymentsFragment

class PaymentsFlowFragment : BaseFlowFragment<PaymentFlowViewModel, LayoutFlowBinding>() {
    override val vmClass = PaymentFlowViewModel::class.java
    override fun initFlow() {
        showFragment(PaymentsFragment.newInstance())
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutFlowBinding =
        LayoutFlowBinding::inflate

    companion object {
        fun newInstance() = PaymentsFlowFragment()
    }
}