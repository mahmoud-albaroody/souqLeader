package com.alef.souqleader.data.di.networkModule

import android.content.Context
import android.util.Log
import com.alef.souqleader.ui.SouqLeaderApp
import com.alef.souqleader.data.remote.APIs
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.constants.Constants.BASE_URL

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideBaseUrlInterceptor(): BaseUrlInterceptor {
        return BaseUrlInterceptor()
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): SouqLeaderApp {
        return app as SouqLeaderApp
    }

    @Singleton
    @Provides
    fun provideContext(application: SouqLeaderApp): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): APIs {
        return retrofit.create(APIs::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideGymsUseCase(
//        apiService: ApiRepoImpl,
//    ): GetLeadUseCase {
//        return GetLeadUseCase(
//            apiService
//        )
//    }



//    @Singleton
//    @Provides
//    fun getDatabaseOo(@ApplicationContext app: Context): CartDatabase {
//        return Room.databaseBuilder(
//            app.applicationContext,
//            CartDatabase::class.java,
//            "cart.db"
//        ).fallbackToDestructiveMigration()
//            //.addTypeConverter(GsonConverterFactory.create())
//            .build()
//    }


    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB


    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HttpLoggingInterceptor, interceptor: Interceptor,
        cache: Cache,baseUrlInterceptor: BaseUrlInterceptor
    ): OkHttpClient {
        headerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val header = HttpLoggingInterceptor()
        header.apply { header.level = HttpLoggingInterceptor.Level.HEADERS }
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.cache(cache)
        okHttpClientBuilder.addInterceptor(baseUrlInterceptor)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.addInterceptor(interceptor)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {

            val requestBuilder = it.request().newBuilder()
                //hear you can add all headers you want by calling 'requestBuilder.addHeader(name ,  value)'
//                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", AccountData.auth_token.toString())

             .header("Accept-Language", AccountData.lang)

            it.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    internal fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }


}