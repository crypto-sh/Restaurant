package com.restaurant.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
open class BaseUnitTest {

    @get:Rule val mainRule = MainDispatcherRule()

    @get:Rule var instanceTaskExecutorRule = InstantTaskExecutorRule()

    fun <T> LiveData<T>.blockingObserver(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }
        observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }

    fun loadingResource(name : String) : String {
        var content = ""
        this.javaClass.classLoader?.let {
            val reader = InputStreamReader(it.getResourceAsStream(name))
            content = reader.readText()
            reader.close()
        }
        return content
    }
}