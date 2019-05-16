package com.example.babylondemo.di

import android.app.Application
import com.example.babylondemo.BuildConfig
import com.example.data.api.PostsApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideCache(application: Application) = Cache(application.cacheDir, BuildConfig.CACHE_SIZE)

    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideTypiCodeApi(retrofit: Retrofit): PostsApi = retrofit.create(PostsApi::class.java)
}