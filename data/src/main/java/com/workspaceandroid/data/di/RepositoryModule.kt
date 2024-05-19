package com.workspaceandroid.data.di

import com.workspaceandroid.data.api.source.IAuthNetSource
import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import com.workspaceandroid.data.database.source.ProfileDbSource
import com.workspaceandroid.data.mappers.CollectionsNetMapper
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.data.mappers.UserDetailsNetMapper
import com.workspaceandroid.data.repositories.AuthRepository
import com.workspaceandroid.data.repositories.CollectionRepository
import com.workspaceandroid.domain.repositories.IAuthRepository
import com.workspaceandroid.domain.repositories.ICollectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        netSource: IAuthNetSource,
        dbSource: ProfileDbSource,
        sharedPreferences: AppSharedPreferences,
        userDetailsNetMapper: UserDetailsNetMapper
    ): IAuthRepository {
        return AuthRepository(
            netSource = netSource,
            dbSource = dbSource,
            sharedPreferencesSource = sharedPreferences,
            userDetailsNetMapper = userDetailsNetMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideCollectionRepository(
        netSource: IPhrasesNetSource,
        phrasesNetMapper: PhrasesNetMapper,
        collectionsNetMapper: CollectionsNetMapper
    ): ICollectionRepository {
        return CollectionRepository(
            netSource = netSource,
            phrasesNetMapper = phrasesNetMapper,
            collectionsNetMapper = collectionsNetMapper
        )
    }
}