package net.eknm.eknmlogistics.payments

import net.eknm.eknmlogistics.api.paymentsApi.PaymentMethodsRepository
import net.eknm.eknmlogistics.root.RootScope
import javax.inject.Inject

@RootScope
class PaymentMethodCreationService @Inject constructor(
    private val paymentMethodsRepository: PaymentMethodsRepository
) {
    //TODO: Unmock and create method using payment services (WayForPay or LIQPay)
    fun createPaymentMethod() {
        val title = (1..4)
            .map { ('0'..'9').random() }
            .joinToString("")
        val paymentId: String = ((0..31).map { ('0'..'9').random() }.joinToString(""))
        val paymentService = "LIQPay"
        paymentMethodsRepository.addPaymentMethod(
            title,
            paymentId,
            paymentService
        )
    }
}