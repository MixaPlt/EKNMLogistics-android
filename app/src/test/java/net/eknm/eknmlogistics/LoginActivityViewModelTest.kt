package net.eknm.eknmlogistics

import androidx.lifecycle.Observer
import io.reactivex.Flowable
import net.eknm.eknmlogistics.android.Optional
import net.eknm.eknmlogistics.authorization.LoginActivityViewModel
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class LoginActivityViewModelTest : BaseTest() {

    @Mock
    lateinit var mockedAuthorizationRepository: AuthorizationRepository

    @Mock
    lateinit var changeFlowToRootObserver: Observer<Unit>

    lateinit var loginActivityViewModel: LoginActivityViewModel

    override fun init() {
        loginActivityViewModel = LoginActivityViewModel(mockedAuthorizationRepository)
        loginActivityViewModel.changeFlowToRootEvent.observeForever(changeFlowToRootObserver)
    }

    @Test
    fun shouldFinishFlowOnAuthorize() {
        `when`(mockedAuthorizationRepository.trackSession()).thenReturn(Flowable.just(Optional("309")))
        loginActivityViewModel.onAttach()
        verify(changeFlowToRootObserver).onChanged(any())
    }
}
