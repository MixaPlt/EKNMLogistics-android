package net.eknm.eknmlogistics.payments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.payments.paymentFragment.PaymentsFragment

@Module
interface PaymentsFlowModule {

    @ContributesAndroidInjector
    fun paymentsFragment(): PaymentsFragment
}