package net.eknm.eknmlogistics.payments.paymentFragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_payments.*
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentPaymentsBinding

class PaymentsFragment : BaseFragment<PaymentsViewModel, FragmentPaymentsBinding>() {
    override val vmClass = PaymentsViewModel::class.java
    override val layoutResId = R.layout.fragment_payments

    private val adapter get() = paymentsRecycler.adapter as PaymentsRecyclingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentsRecycler.adapter = PaymentsRecyclingAdapter()
        paymentsRecycler.layoutManager = LinearLayoutManager(context)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.paymentMethods.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })
    }

    companion object {
        fun newInstance() = PaymentsFragment()
    }
}