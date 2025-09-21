package com.adygyes.di

import com.adygyes.data.repository.AttractionRepositoryImpl
import com.adygyes.domain.repository.AttractionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindAttractionRepository(
        attractionRepositoryImpl: AttractionRepositoryImpl
    ): AttractionRepository
}
