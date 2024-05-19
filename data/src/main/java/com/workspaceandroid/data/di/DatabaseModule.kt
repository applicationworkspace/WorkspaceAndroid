package com.workspaceandroid.data.di

import android.content.Context
import androidx.room.Room
import com.workspaceandroid.data.database.room.WorkspaceRoomDatabase
import com.workspaceandroid.data.database.room.dao.ProfileDao
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import com.workspaceandroid.data.database.sharedpreferences.WorkspaceSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): AppSharedPreferences = WorkspaceSharedPreferences(context)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): WorkspaceRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            WorkspaceRoomDatabase::class.java,
            "workspace_room_db"
        ).build()
    }

    // DAOs
    @Provides
    fun provideProfileDao(appDatabase: WorkspaceRoomDatabase): ProfileDao {
        return appDatabase.profileDao()
    }
}