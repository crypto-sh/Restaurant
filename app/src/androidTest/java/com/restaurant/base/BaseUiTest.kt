package com.restaurant.base

import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.restaurant.core.dto.Restaurant
import com.restaurant.core.utils.compareTo
import com.restaurant.ui.adapter.RvAdapterRestaurant
import org.hamcrest.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {

    @get:Rule var countingTaskRule = CountingTaskExecutorRule()

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun before() {

    }

    @After
    open fun after() {

    }

    fun childOf(view : Matcher<View>, childPosition : Int) : Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("position $childPosition of parent")
                view.describeTo(description)
            }

            override fun matchesSafely(item: View): Boolean {
                if (item.parent !is ViewGroup) return false
                val parent = item.parent as ViewGroup
                return (view.matches(parent) && parent.childCount > childPosition && parent.getChildAt(childPosition) == item)
            }
        }
    }

    fun <T> isSorted(desc : Boolean = true, value : (T) -> Number): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            private fun extractTeamNames(list: List<T>): List<Number> {
                val values : MutableList<Number> = ArrayList()
                list.forEach {
                    values .add(value(it))
                }
                return values
            }

            override fun describeTo(description: Description?) {
                description?.appendText("has items sorted the list")
            }

            override fun matchesSafely(item: View?): Boolean {
                val recyclerView = item as RecyclerView
                @Suppress("UNCHECKED_CAST") val adapter = recyclerView.adapter as ListAdapter<T, *>
                val list = extractTeamNames(adapter.currentList as List<T>)
                return list.zipWithNext { a, b ->
                    println("::::::::::::::::::: ")

                    if(desc) a >= b else a <= b
                }.all { it }
            }
        }
    }

    fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter?.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        latch.countDown()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                }
            )
        }
        countingTaskRule.drainTasks(1, TimeUnit.SECONDS)
        if ((recyclerView.adapter?.itemCount ?: 0) > 0) {
            return
        }
        MatcherAssert.assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
    }
}