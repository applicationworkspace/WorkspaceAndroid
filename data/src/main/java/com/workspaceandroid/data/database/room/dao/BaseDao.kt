package com.workspaceandroid.data.database.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(list: List<T>)

    @Delete
    suspend fun delete(obj: T)

    @Delete
    suspend fun delete(obj: List<T>)
}