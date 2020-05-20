package net.eknm.eknmlogistics.root

import dagger.Binds
import dagger.Module
import net.eknm.eknmlogistics.EknmLogisticsApplication
import net.eknm.eknmlogistics.authorization.authorizationRepository.LocalUserSource

@Module(includes = [RepositoryModule.Impl::class])
interface RepositoryModule {

    @Binds
    fun localUserSource(application: EknmLogisticsApplication): LocalUserSource

    @Module
    class Impl
}