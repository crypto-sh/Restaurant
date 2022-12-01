package com.restaurant

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.restaurant.ui.RestaurantActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RestaurantFeatureTest : BaseUiTest() {

    lateinit var scenario : ActivityScenario<RestaurantActivity>

    override fun before() {
        super.before()
        scenario = ActivityScenario.launch(RestaurantActivity::class.java)
    }

    @Test
    fun showTitle() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        assertDisplayed("Restaurant")
    }

    @Test
    fun showingProgressUntilLoadingRestaurants() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.loading_progress_waiting)).check(matches(isDisplayed()))
    }



}