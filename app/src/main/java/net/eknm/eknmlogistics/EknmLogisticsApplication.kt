package net.eknm.eknmlogistics

import android.preference.PreferenceManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.processors.BehaviorProcessor
import net.eknm.eknmlogistics.android.Optional
import net.eknm.eknmlogistics.android.singleThreadLazy
import net.eknm.eknmlogistics.authorization.authorizationRepository.LocalUserSource
import net.eknm.eknmlogistics.root.DaggerAppComponent

class EknmLogisticsApplication : DaggerApplication(), LocalUserSource {
    private val appComponent by singleThreadLazy {
        DaggerAppComponent.builder()
            .application(this)
            .build().apply {
                inject(this@EknmLogisticsApplication)
            }
    }

    @Suppress("DEPRECATION")
    private val prefs by singleThreadLazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun saveUserToken(token: String?) {
        if (token != tokenProcessor.value?.item) {
            prefs.edit().putString(KEY_SESSION, token).apply()
            tokenProcessor.onNext(Optional(token))
        }
    }

    override fun trackUserToken() = tokenProcessor

    private val tokenProcessor by singleThreadLazy {
        BehaviorProcessor.create<Optional<String>>().apply {
            onNext(Optional(prefs.getString(KEY_SESSION, null)))
        }
    }

    override fun getUserToken(): String? = tokenProcessor.value?.item

    companion object {
        private const val KEY_SESSION = "session"
    }
}