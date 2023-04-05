package com.test.testceibalistusers.data.di

import com.test.testceibalistusers.data.repository.RepositoryApi
import com.test.testceibalistusers.data.repository.RepositoryApiImpl
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
    abstract fun repositoryApi(repository: RepositoryApiImpl): RepositoryApi

}