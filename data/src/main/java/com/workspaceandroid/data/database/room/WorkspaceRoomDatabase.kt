package com.workspaceandroid.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.workspaceandroid.data.database.room.dao.ProfileDao
import com.workspaceandroid.data.database.room.dto.ProfileDbDTO

@Database(
    entities = [
        ProfileDbDTO::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
//    DateTimeTypeConverters::class
)

abstract class WorkspaceRoomDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}