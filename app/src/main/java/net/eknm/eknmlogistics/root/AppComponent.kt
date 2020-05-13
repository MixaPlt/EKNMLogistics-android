package net.eknm.eknmlogistics.root

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import net.eknm.eknmlogistics.EknmLogisticsApplication
import net.eknm.eknmlogistics.api.ApiModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        RepositoryModule::class,
        ActivityProviderModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<EknmLogisticsApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: EknmLogisticsApplication): Builder

        fun build(): AppComponent
    }

    override fun inject(application: EknmLogisticsApplication)
}