package com.workspaceandroid.data.mappers

import com.workspaceandroid.data.dto.auth.UserDetailsNetDTO
import com.workspaceandroid.domain.models.auth.UserDetails
import javax.inject.Inject

class UserDetailsNetMapper @Inject constructor():
    EntityMapper<UserDetailsNetDTO, UserDetails> {
    override fun mapFromEntity(entity: UserDetailsNetDTO): UserDetails {
        return UserDetails(
            id = entity.id ?: -1,
            name = entity.name.orEmpty(),
            email = entity.email.orEmpty()
        )
    }
}