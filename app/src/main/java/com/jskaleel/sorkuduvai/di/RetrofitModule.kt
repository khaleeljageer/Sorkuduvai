package com.jskaleel.sorkuduvai.di

import android.content.Context
import com.jskaleel.sorkuduvai.BuildConfig
import com.jskaleel.sorkuduvai.R
import com.jskaleel.sorkuduvai.utils.extensions.isOnline
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_PRAGMA = "Pragma"
    private const val CACHE_SIZE = 100 * 1024 * 1024.toLong()
    private const val MAX_AGE = 10
    private const val MAX_STALE = 15 // No days cache works when offline mode

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        @Named("networkInterceptor") networkInterceptor: Interceptor,
        @Named("offlineInterceptor") offlineInterceptor: Interceptor,
        @Named("header") headers: Interceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.apply {
            cache(cache)
            addInterceptor(offlineInterceptor)
            addNetworkInterceptor(networkInterceptor)
            addInterceptor(headers)
            build()
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
            return build()
        }
    }

    @Provides
    @Singleton
    fun providesCache(@ApplicationContext context: Context): Cache {
        return Cache(File(context.cacheDir, context.getString(R.string.app_name)), CACHE_SIZE)
    }

    @Provides
    @Singleton
    @Named("header")
    fun providesHeaders(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Host", "sorkuvai.com:9001")
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Origin", "https://sorkuvai.com")
                .header("Connection", "keep-alive")
                .header("Referer", "https://sorkuvai.com/")
                .header(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:95.0) Gecko/20100101 Firefox/95.0"
                )
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    @Named("networkInterceptor")
    fun providesNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(
                    MAX_AGE,
                    TimeUnit.SECONDS
                )
                .build()
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    @Provides
    @Singleton
    @Named("offlineInterceptor")
    fun providesOfflineInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!context.isOnline()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(MAX_STALE, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }
}
