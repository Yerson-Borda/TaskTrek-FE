package com.tasktrek.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.tasktrek.utils.AuthInterceptor
import com.tasktrek.utils.LocalDateTimeTypeAdapter
import com.tasktrek.utils.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.52.8:8080/"

    fun create(context: Context): Retrofit {
        val tokenManager = TokenManager(context)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun provideServices(context: Context): ServiceContainer {
        val retrofit = create(context)
        return ServiceContainer(
            authApiService = retrofit.create(AuthApiService::class.java),
            taskApiService = retrofit.create(TaskApiService::class.java),
            projectApiService = retrofit.create(ProjectApiService::class.java),
            profileApiService = retrofit.create(ProfileApiService::class.java)
        )
    }

    data class ServiceContainer(
        val authApiService: AuthApiService,
        val taskApiService: TaskApiService,
        val projectApiService: ProjectApiService,
        val profileApiService: ProfileApiService
    )
}