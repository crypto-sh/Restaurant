package com.restaurant

import com.restaurant.base.BaseUnitTest
import com.restaurant.base.createRestaurants
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.ui.viewModel.RestaurantViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class RestaurantViewModelTest : BaseUnitTest() {

    private val expected = createRestaurants(5)
    private val exception = NullPointerException("There is no file exist like the one you want.")
    private var repository: RestaurantRepository = mock()

    private val viewModel = RestaurantViewModel(repository)

    @Test
    fun testCallingRepositoryWithLoadingRestaurants() = runTest {
        viewModel.onCreate(mock())
        verify(repository, times(1)).restaurantList(any(), any())
    }

    @Test
    fun testLoadRestaurantsSuccess() = runTest {
        whenever(repository.restaurantList()).thenReturn(flow {
            emit(Result.success(expected))
        })
        viewModel.onCreate(mock())
        assertEquals(expected.size, viewModel.observableRestaurants().blockingObserver()?.size)
    }


    @Test
    fun testLoadRestaurantsFail() = runTest {
        whenever(repository.restaurantList()).thenReturn(flow {
            emit(Result.failure(exception))
        })
        viewModel.onCreate(mock())
        assertNull(viewModel.observableRestaurants().blockingObserver())
        assertEquals(exception, viewModel.observableError().blockingObserver())
    }
}