package com.example.restaurantsapp.di


import android.content.Context
import com.example.restaurantsapp.data.local.DataParser
import com.example.restaurantsapp.domain.repository.GetSearchedItemRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun providesDataParser(@ApplicationContext appContext: Context, gson: Gson): DataParser{
        return DataParser(gson, appContext)
    }

    @Singleton
    @Provides
    fun provideRepository(dataParser: DataParser): GetSearchedItemRepository{
        return com.example.restaurantsapp.data.repository.GetSearchedItemRepository(dataParser)
    }

}