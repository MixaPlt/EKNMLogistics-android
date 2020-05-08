package net.eknm.eknmlogistics.root

import dagger.Module

@Module(includes = [RepositoryModule.Impl::class])
interface RepositoryModule {

    @Module
    class Impl
}