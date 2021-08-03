package com.example.comics.comic

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    fun provideConverterFactory(): Converter.Factory {
      return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideMarvelService(converter: Converter.Factory): MarvelService {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(converter)
            .build()
            .create(MarvelService::class.java)
    }
}