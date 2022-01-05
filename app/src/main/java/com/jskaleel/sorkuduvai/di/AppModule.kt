package com.jskaleel.sorkuduvai.di

import android.content.Context
import androidx.room.Room
import com.jskaleel.sorkuduvai.BuildConfig
import com.jskaleel.sorkuduvai.db.SorkuduvaiDataBase
import com.jskaleel.sorkuduvai.db.dao.RecentSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): SorkuduvaiDataBase {
        return Room.databaseBuilder(context, SorkuduvaiDataBase::class.java, "sorkuduvai_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSearchDao(appDataBase: SorkuduvaiDataBase): RecentSearchDao {
        return appDataBase.recentSearchDao()
    }
}
