package com.alokomkar.remote.di

import android.content.Context
import com.alokomkar.remote.BuildConfig
import com.alokomkar.remote.api.CurrencyAPI
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Suppress("PrivatePropertyName")
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val BASE_URL = "http://api.currencylayer.com/"
    private val KEY_API = "access_key"
    private val API_KEY = BuildConfig.API_KEY
    private val TIMEOUT_SECOND: Long = 15

    @Singleton
    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyAPI
        = retrofit.create(CurrencyAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit
        = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient
        = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
        .addInterceptor { interceptorChain ->
            val builder = interceptorChain.request().newBuilder()
            val url = interceptorChain.request().url()
            val request = builder.url(
                url.newBuilder().addQueryParameter(KEY_API, API_KEY).build()
            ).build()
            interceptorChain.proceed(request)
        }
        .addInterceptor(chuckerInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        // Create the Interceptor
        return ChuckerInterceptor.Builder(context)
            // The previously created Collector
            .collector(chuckerCollector)
            // The max body content length in bytes, after this responses will be truncated.
            .maxContentLength(250_000L)
            // List of headers to replace with ** in the Chucker UI
            .redactHeaders("Auth-Token", "Bearer")
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            .build()
    }


}