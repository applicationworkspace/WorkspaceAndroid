package com.workspaceandroid.data.database.room.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.workspaceandroid.data.database.room.Table

@Entity(tableName = Table.PROFILE)
data class ProfileDbDTO(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val email: String?
)


