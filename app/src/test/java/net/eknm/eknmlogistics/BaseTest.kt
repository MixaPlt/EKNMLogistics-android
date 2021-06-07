package net.eknm.eknmlogistics

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

@RunWith(JUnit4::class)
abstract class BaseTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = TestSchedulerRule()

    @Before
    abstract fun init()
}
