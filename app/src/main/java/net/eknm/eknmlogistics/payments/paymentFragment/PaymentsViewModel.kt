package net.eknm.eknmlogistics.payments.paymentFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.api.paymentsApi.PaymentMethodsRepository
import net.eknm.eknmlogistics.payments.PaymentMethod
import net.eknm.eknmlogistics.payments.PaymentMethodCreationService
import javax.inject.Inject

class PaymentsViewModel @Inject constructor(
    private val paymentMethodsRepository: PaymentMethodsRepository,
    private val paymentMethodCreationService: PaymentMethodCreationService
) : BaseFragmentViewModel() {

    private val _paymentMethods = MutableLiveData<List<PaymentMethod>>()
    val paymentMethods: LiveData<List<PaymentMethod>> = _paymentMethods

    fun addPaymentMethod() {
        paymentMethodCreationService.createPaymentMethod()
    }

    override fun onInitialize() {
        super.onInitialize()
        executeDisposable {
            paymentMethodsRepository
                .trackPaymentMethods()
                .ioToMain()
                .subscribe {
                    _paymentMethods.value = it
                }
        }
    }
}