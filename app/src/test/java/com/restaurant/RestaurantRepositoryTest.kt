package com.restaurant

import com.restaurant.base.BaseUnitTest
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.repository.impl.RestaurantRepositoryImpl
import com.restaurant.core.utils.FileHelper
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
    private val repository : RestaurantRepository = RestaurantRepositoryImpl(fileHelper)

    @Test
    fun testLoadingRestaurantsSuccess() = runTest {
        whenever(fileHelper.load(any())).then {
            loadingResource("sample_restaurant_with_three_items_success.json")
        }
        val result = repository.restaurantList().first()
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertEquals(3, list?.size)
    }

    @Test
    fun testLoadingRestaurantsFail() = runTest {
        whenever(fileHelper.load(any())).thenThrow(exception)
        val result = repository.restaurantList().first()
        assertFalse(result.isSuccess)
        assertEquals(exception, result.exceptionOrNull())
    }
}