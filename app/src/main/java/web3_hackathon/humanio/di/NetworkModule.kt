package web3_hackathon.humanio.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule  {
    private val isDebug: Boolean = false
    private const val apiToken: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbklkeCI6MTkyMjk0LCJ0b2tlblR5cGUiOiJXQUxMRVQiLCJ0b2tlbkV4cGlyZWREYXRlIjoiMjAyMy0xMC0yMVQxMTo0NjowNy44MzJaIn0.ERsVBCzmL0k9SNbqZdZ1Rud6bx2BaKy-71S2ZyUD1Xw"

    private val interceptorProvider: InterceptorProviderContract by lazy {
        InterceptorProvider(isDebug, apiToken)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptorProvider.provideAccessInterceptor())
            .addInterceptor(interceptorProvider.provideLoggingInterceptor())
            .build()


    @Provides
    @Singleton
    fun provideHumanIOApi(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    val BASE_URL = "https://octet-api.blockchainapi.io/2.0/"

//    companion object {
//        const val BASE_URL = "https://octet-api.blockchainapi.io/2.0/"
//
//        @Synchronized
//        fun newInstance(isDebug: Boolean, apiToken: String) =
//            web3_hackathon.humanio.di.NetworkModule(isDebug, apiToken)
//    }
}