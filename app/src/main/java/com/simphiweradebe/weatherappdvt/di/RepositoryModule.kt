package com.simphiweradebe.weatherappdvt.di

import com.simphiweradebe.weatherappdvt.data.remote.WeatherApi
import com.simphiweradebe.weatherappdvt.data.repository.WeatherRepositoryImpl
import com.simphiweradebe.weatherappdvt.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
}
