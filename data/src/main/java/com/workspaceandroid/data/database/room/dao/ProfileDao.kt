package com.workspaceandroid.data.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.workspaceandroid.data.database.room.Table
import com.workspaceandroid.data.database.room.dto.ProfileDbDTO

@Dao
abstract class ProfileDao : BaseDao<ProfileDbDTO> {

    @Query("SELECT * FROM ${Table.PROFILE} LIMIT 1")
    abstract suspend fun getProfile(): ProfileDbDTO?

//    @Query("SELECT * FROM ${Table.PROFILE} LIMIT 1")
//    abstract fun getProfileFlow(): Flow<ProfileDbDTO?>

    @Query("DELETE FROM ${Table.PROFILE}")
    abstract suspend fun clearTable()

    @Transaction
    open suspend fun updateProfile(profile: ProfileDbDTO) {
        clearTable()
        insertOrUpdate(profile)
    }
}