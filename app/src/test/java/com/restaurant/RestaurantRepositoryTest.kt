package com.restaurant

import com.restaurant.base.BaseUnitTest
import com.restaurant.core.dto.SortType
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.repository.impl.RestaurantRepositoryImpl
import com.restaurant.core.utils.FileHelper
import com.restaurant.core.utils.PreferenceHandler
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RestaurantRepositoryTest : BaseUnitTest() {

    private val exception = NullPointerException("there is no file to load")
    private val fileHelper : FileHelper = mock()
    private val preferenceHandler : PreferenceHandler = mock()
    private val repository : RestaurantRepository = RestaurantRepositoryImpl(fileHelper, preferenceHandler)

    @Test
    fun testLoadingRestaurantsSuccess() = runTest {
        mockRestaurantsSuccessCase()
        val result = repository.restaurantList().first()
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertEquals(4, list?.size)
    }

    @Test
    fun testLoadingRestaurantsFilterSuccess() = runTest {
        mockRestaurantsSuccessCase()
        val result = repository.restaurantList("Royal").first()
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertEquals(1, list?.size)
    }

    @Test
    fun testLoadingRestaurantsSortOpenSuccess() = runTest {
        mockRestaurantsSuccessCase()
        val result = repository.restaurantList(sort = SortType.OpenStatus).first()
        val items = result.getOrNull() ?: listOf()
        assertEquals(4, items.size)
        assertEquals("open", items[0].status)
        assertEquals("open", items[1].status)
        assertNotSame("open", items[2].status)
    }

    @Test
    fun testLoadingRestaurantsSortPopularityDescSuccess() = runTest {
        mockRestaurantsSuccessCase()
        val result = repository.restaurantList(sort = SortType.Popularity).first()
        val items = result.getOrNull() ?: listOf()
        assertEquals(4, items.size)
        for (index in 0 until items.size - 1) {
            val first = items[index].sortingValues.popularity
            val second = items[index + 1].sortingValues.popularity
            assertTrue(first >= second)
        }
    }

    @Test
    fun testLoadingRestaurantsSortDistanceASCSuccess() = runTest {
        mockRestaurantsSuccessCase()
        val result = repository.restaurantList(sort = SortType.Distance).first()
        val items = result.getOrNull() ?: listOf()
        assertEquals(4, items.size)
        for (index in 0 until items.size - 1) {
            val first = items[index].sortingValues.distance
            val second = items[index + 1].sortingValues.distance
            assertTrue(first <= second)
        }
    }

    @Test
    fun testSavedPreferenceFirstTime() = runTest {
        whenever(preferenceHandler.getString(any(), any())).thenReturn("")
        val sort = repository.savedSortType()
        assertEquals(SortType.Undefine, sort)
    }

    @Test
    fun testSavedPreferenceSecondTime() = runTest {
        whenever(preferenceHandler.getString(any(), any())).thenReturn("1")
        val sort = repository.savedSortType()
        assertEquals(SortType.OpenStatus, sort)
    }

    @Test
    fun testLoadingRestaurantsFail() = runTest {
        whenever(fileHelper.load(any())).thenThrow(exception)
        val result = repository.restaurantList().first()
        assertFalse(result.isSuccess)
        assertEquals(exception, result.exceptionOrNull())
    }

    private fun mockRestaurantsSuccessCase() {
        whenever(fileHelper.load(any())).then {
            loadingResource("sample_restaurant_with_three_items_success.json")
        }
        whenever(preferenceHandler.getString(any(), any())).thenReturn("")
    }
}