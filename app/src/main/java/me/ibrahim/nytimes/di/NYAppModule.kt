package me.ibrahim.nytimes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.ibrahim.nytimes.BuildConfig
import me.ibrahim.nytimes.data.managers.SharedPrefsManagerImpl
import me.ibrahim.nytimes.data.remote.api.NYTimesApi
import me.ibrahim.nytimes.data.repositories.NYTimesRepositoryImpl
import me.ibrahim.nytimes.data.utils.DataMapperImpl
import me.ibrahim.nytimes.domain.managers.SharedPrefsManager
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import me.ibrahim.nytimes.domain.utils.DataMapper
import me.ibrahim.nytimes.utils.AppConstants
import me.ibrahim.nytimes.utils.AppConstants.APP_PREFS
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

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {

        return if (BuildConfig.DEBUG) {
            context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        } else {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                APP_PREFS,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    @Provides
    @Singleton
    fun provideSharedPrefsManager(sharedPrefs: SharedPreferences): SharedPrefsManager {
        return SharedPrefsManagerImpl(sharedPrefs = sharedPrefs)
    }

}