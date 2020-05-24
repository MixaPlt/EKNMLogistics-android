package net.eknm.eknmlogistics.payments.paymentFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.BaseRecyclerAdapter
import net.eknm.eknmlogistics.payments.PaymentMethod

class PaymentsRecyclingAdapter : BaseRecyclerAdapter<PaymentMethod>() {
    override fun getLayoutIdForPosition(position: Int) = R.layout.layout_payment_method
}