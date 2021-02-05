package net.eknm.eknmlogistics.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface HomePaddingManager {
    val bottomPadding: LiveData<Int>
    fun setBottomPadding(bottomPadding: Int)
}

class HomePaddingManagerImpl: HomePaddingManager {
    private val _bottomPadding = MutableLiveData<Int>()
    override val bottomPadding: LiveData<Int> = _bottomPadding

    override fun setBottomPadding(bottomPadding: Int) {
        _bottomPadding.value = bottomPadding
    }
}