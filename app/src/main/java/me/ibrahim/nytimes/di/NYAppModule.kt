package me.ibrahim.nytimes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ibrahim.nytimes.data.remote.api.NYTimesApi
import me.ibrahim.nytimes.data.repositories.NYTimesRepositoryImpl
import me.ibrahim.nytimes.data.utils.DataMapperImpl
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import me.ibrahim.nytimes.domain.utils.DataMapper
import me.ibrahim.nytimes.utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NYAppModule {

    @Provides
    @Singleton
    fun provideNYTimesApi(): NYTimesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NYTimesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataMapper(): DataMapper {
        return DataMapperImpl()
    }

    @Provides
    @Singleton
    fun provideNYTimesRepository(
        nyTimesApi: NYTimesApi,
        dataMapper: DataMapper
    ): NYTimesRepository {
        return NYTimesRepositoryImpl(nyTimesApi = nyTimesApi, dataMapper = dataMapper)
    }
}