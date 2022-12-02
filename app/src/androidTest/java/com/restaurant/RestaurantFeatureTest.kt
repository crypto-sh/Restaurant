package com.restaurant

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.restaurant.base.BaseUiTest
import com.restaurant.core.dto.Restaurant
import com.restaurant.ui.RestaurantActivity
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RestaurantFeatureTest : BaseUiTest() {

    lateinit var scenario: ActivityScenario<RestaurantActivity>

    override fun before() {
        super.before()
        scenario = ActivityScenario.launch(RestaurantActivity::class.java)
    }

    @Test
    fun test_00_showTitle() {
        assertDisplayed("Restaurant")
    }

    @Test
    fun test_01_showRestaurantList() {
        var recyclerView: RecyclerView? = null
        scenario.onActivity {
            recyclerView = it.binding.restaurantRecyclerView
        }
        assertNotNull(recyclerView)
        waitForAdapterChange(recyclerView!!)
        onView(withId(R.id.loading_progress_waiting)).check(matches(not(isDisplayed())))
        onView(
            allOf(
                withId(R.id.restaurant_list_name_textView),
                isDescendantOfA(childOf(withId(R.id.restaurant_recyclerView), 0))
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.restaurant_list_status_textView),
                isDescendantOfA(childOf(withId(R.id.restaurant_recyclerView), 0))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_02_showingRestaurantSearchIcon() {
        assertListItemCount(R.id.restaurant_recyclerView, 19)
        onView(withId(R.id.menu_search)).check(matches(isDisplayed())).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("Tan"))
        assertListItemCount(R.id.restaurant_recyclerView, 2)
    }

    @Test
    fun test_03_showingRestaurantSortIcon() {
        onView(withId(R.id.menu_sort)).check(matches(isDisplayed())).perform(click())
        var openStatus = ""
        var popular = ""
        var distance = ""
        scenario.onActivity {
            openStatus = it.getString(R.string.popup_menu_show_open_status)
            popular    = it.getString(R.string.popup_menu_desc_popular)
            distance   = it.getString(R.string.popup_menu_asc_distance)
        }
        onView(withText(openStatus)).inRoot(isPlatformPopup()).check(matches(isDisplayed()))
        onView(withText(popular)).inRoot(isPlatformPopup()).check(matches(isDisplayed()))
        onView(withText(distance)).inRoot(isPlatformPopup()).check(matches(isDisplayed()))
    }

    @Test
    fun test_04_changingTheListOrderBaseOnOpenStatus() {
        onView(withId(R.id.menu_sort)).check(matches(isDisplayed())).perform(click())
        onView(withText("Open status")).inRoot(isPlatformPopup()).perform(click())
        val status = "open"
        onView(
            allOf(
                withId(R.id.restaurant_list_status_textView),
                isDescendantOfA(childOf(withId(R.id.restaurant_recyclerView), 0))
            )
        ).check(matches(withText(status)))
        onView(
            allOf(
                withId(R.id.restaurant_list_status_textView),
                isDescendantOfA(childOf(withId(R.id.restaurant_recyclerView), 1))
            )
        ).check(matches(withText(status)))
        onView(
            allOf(
                withId(R.id.restaurant_list_status_textView),
                isDescendantOfA(childOf(withId(R.id.restaurant_recyclerView), 2))
            )
        ).check(matches(withText(status)))
    }

    @Test
    fun test_05_changingTheListOrderBaseOnPopularityDesc() {
        onView(withId(R.id.menu_sort)).check(matches(isDisplayed())).perform(click())
        onView(withText("Popularity descending")).inRoot(isPlatformPopup()).perform(click())
        onView(withId(R.id.restaurant_recyclerView)).check(matches(isSorted<Restaurant>(true) {
            it.sortingValues.popularity
        }))
    }

    @Test
    fun test_06_changingTheListOrderBaseOnDistanceAsc() {
        onView(withId(R.id.menu_sort)).check(matches(isDisplayed())).perform(click())
        onView(withText("Distance ascending")).inRoot(isPlatformPopup()).perform(click())
        onView(withId(R.id.restaurant_recyclerView)).check(matches(isSorted<Restaurant>(false) {
            it.sortingValues.distance
        }))
    }

    @Test
    fun test_07_previousSortSaved() {
        onView(withId(R.id.restaurant_recyclerView)).check(matches(isSorted<Restaurant>(false) {
            it.sortingValues.distance
        }))
    }


}