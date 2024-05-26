package com.workspaceandroid.data.mappers

import com.workspaceandroid.data.dto.phrases.GroupModelNetDTO
import com.workspaceandroid.domain.models.phrase.GroupModel
import javax.inject.Inject

class GroupNetMapper @Inject constructor(
    private val phrasesNetMapper: PhrasesNetMapper,
) : EntityMapper<GroupModelNetDTO, GroupModel> {

    override fun mapFromEntity(entity: GroupModelNetDTO): GroupModel {
        return GroupModel(
            id = entity.id ?: -1,
            colorHex = entity.colorHex ?: "FFFFFF",
            name = entity.name.orEmpty(),
            description = entity.description.orEmpty(),
            phrases = phrasesNetMapper.mapListFrom(entity.phrases.orEmpty())
        )
    }

    override fun mapToEntity(domainModel: GroupModel): GroupModelNetDTO {
        return GroupModelNetDTO(
            id = domainModel.id,
            colorHex = domainModel.colorHex,
            name = domainModel.name,
            description = domainModel.description,
            phrases = emptyList()
        )
    }
}