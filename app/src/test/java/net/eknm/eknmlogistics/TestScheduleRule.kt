package net.eknm.eknmlogistics

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestSchedulerRule : TestRule {
    private val synchronousScheduler = Schedulers.trampoline()

    val testScheduler = TestScheduler()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { synchronousScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { synchronousScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { synchronousScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { synchronousScheduler }

                base.evaluate()
            }
        }
    }
}
