package net.eknm.eknmlogistics.api.paymentsApi

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import net.eknm.eknmlogistics.android.ioToIo
import net.eknm.eknmlogistics.payments.PaymentMethod
import net.eknm.eknmlogistics.root.RootScope
import javax.inject.Inject

@RootScope
class PaymentMethodsRepository @Inject constructor(
    private val paymentsApi: PaymentsApi
) {
    private val paymentMethodsProcessor = BehaviorProcessor.create<List<PaymentMethod>>()

    fun trackPaymentMethods(): Flowable<List<PaymentMethod>> {
        updatePaymentMethods()
        return paymentMethodsProcessor
    }

    @SuppressLint("CheckResult")
    fun addPaymentMethod(
        title: String,
        paymentId: String,
        paymentService: String
    ) {
        paymentsApi.addPaymentMethod(
            PaymentMethodInsertBody(title, paymentId, paymentService)
        )
            .ioToIo()
            .doFinally { updatePaymentMethods() }
            .subscribe({

            }, {

            })
    }

    @SuppressLint("CheckResult")
    private fun updatePaymentMethods() {
        paymentsApi
            .getMyPaymentMethods()
            .ioToIo()
            .subscribe({
                paymentMethodsProcessor.onNext(it)
            }, { })
    }
}