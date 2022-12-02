package com.restaurant.di

import android.app.Application
import com.restaurant.core.repository.RestaurantRepository
import com.restaurant.core.repository.impl.RestaurantRepositoryImpl
import com.restaurant.core.utils.FileHelper
import com.restaurant.core.utils.PreferenceHandler
import com.restaurant.core.utils.PreferenceHandlerImpl
import com.restaurant.ui.viewModel.RestaurantViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFileHelper(application: Application): FileHelper {
        return FileHelper(application)
    }

    @Provides
    fun providePreferenceHandler(application: Application): PreferenceHandler {
        return PreferenceHandlerImpl(application)
    }

    @Provides
    fun provideRestaurantRepository(fileHelper: FileHelper, preferenceHandler: PreferenceHandler): RestaurantRepository {
        return RestaurantRepositoryImpl(fileHelper, preferenceHandler)
    }

    @Provides
    fun provideRestaurantViewModelFactory(repository: RestaurantRepository): RestaurantViewModelFactory {
        return RestaurantViewModelFactory(repository)
    }
}