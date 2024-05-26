package com.workspaceandroid.data.di

import com.workspaceandroid.data.api.source.ICollectionNetSource
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.data.common.TimeHelper
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.data.repositories.NotificationRepository
import com.workspaceandroid.domain.repositories.INotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideTimeHelper(): ITimeHelper = TimeHelper()

    @Singleton
    @Provides
    fun provideNotificationRepository(
        netSource: ICollectionNetSource,
        phrasesNetMapper: PhrasesNetMapper
    ): INotificationRepository {
        return NotificationRepository(
            netSource = netSource,
            phrasesNetMapper = phrasesNetMapper
        )
    }
}