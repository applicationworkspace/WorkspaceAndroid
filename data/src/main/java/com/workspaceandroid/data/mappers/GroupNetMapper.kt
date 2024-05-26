package com.workspaceandroid.data.mappers

import com.workspaceandroid.data.dto.phrases.GroupNetDTO
import com.workspaceandroid.domain.models.phrase.GroupModel
import javax.inject.Inject

class GroupNetMapper @Inject constructor(
    private val phrasesNetMapper: PhrasesNetMapper,
) : EntityMapper<GroupNetDTO, GroupModel> {

    override fun mapFromEntity(entity: GroupNetDTO): GroupModel {
        return GroupModel(
            id = entity.id ?: -1,
            colorHex = entity.colorHex ?: "FFFFFF",
            name = entity.name.orEmpty(),
            description = entity.description.orEmpty(),
            phrases = phrasesNetMapper.mapListFrom(entity.phrases.orEmpty())
        )
    }

    override fun mapToEntity(domainModel: GroupModel): GroupNetDTO {
        return GroupNetDTO(
            id = domainModel.id,
            colorHex = domainModel.colorHex,
            name = domainModel.name,
            description = domainModel.description,
            phrases = emptyList()
        )
    }
}