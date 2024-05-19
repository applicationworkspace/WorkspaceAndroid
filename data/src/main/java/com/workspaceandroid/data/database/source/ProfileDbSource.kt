package com.workspaceandroid.data.database.source

import com.workspaceandroid.data.database.room.dao.ProfileDao
import com.workspaceandroid.data.database.room.dto.ProfileDbDTO
import javax.inject.Inject

class ProfileDbSource @Inject constructor(private val profileDao: ProfileDao) {

    suspend fun saveUserProfile(profile: ProfileDbDTO) {
        profileDao.updateProfile(profile)
    }

}