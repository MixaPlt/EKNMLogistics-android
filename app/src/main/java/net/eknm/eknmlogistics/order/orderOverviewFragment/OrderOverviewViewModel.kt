package net.eknm.eknmlogistics.order.orderOverviewFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.order.OrderCreationService
import javax.inject.Inject

class OrderOverviewViewModel @Inject constructor(
    private val orderCreationService: OrderCreationService
) : BaseFragmentViewModel() {
    private val _originLiveData = MutableLiveData<String>()
    val originLiveData: LiveData<String> = _originLiveData

    private val _destinationLiveData = MutableLiveData<String>()
    val destinationLiveData: LiveData<String> = _destinationLiveData

    init {
        executeDisposable {
            orderCreationService
                .loadOriginAddress()
                .onErrorReturn {
                    ADDRESS_PLACEHOLDER
                }
                .ioToMain()
                .subscribe { originAddress ->
                    _originLiveData.value = originAddress
                }
        }

        executeDisposable {
            orderCreationService
                .loadDestinationAddress()
                .onErrorReturn {
                    ADDRESS_PLACEHOLDER
                }
                .ioToMain()
                .subscribe { destinationAddress ->
                    _destinationLiveData.value = destinationAddress
                }
        }
    }

    companion object {
        private const val ADDRESS_PLACEHOLDER = "Unknown road"
    }
}