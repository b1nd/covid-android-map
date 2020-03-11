package ru.b1nd.network.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Gson> {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}