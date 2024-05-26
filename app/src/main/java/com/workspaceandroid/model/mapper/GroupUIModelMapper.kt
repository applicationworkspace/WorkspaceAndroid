package com.workspaceandroid.model.mapper

import com.workspaceandroid.data.mappers.EntityMapper
import com.workspaceandroid.domain.models.phrase.GroupModel
import com.workspaceandroid.model.GroupUIModel
import javax.inject.Inject

class GroupUIModelMapper @Inject constructor() : EntityMapper<GroupUIModel, GroupModel> {

    override fun mapFromEntity(entity: GroupUIModel): GroupModel {
        return GroupModel(
            id = -1,
            colorHex = entity.hexColor,
            name = entity.name,
            description = entity.description,
            phrases = emptyList()
        )
    }

    override fun mapToEntity(domainModel: GroupModel): GroupUIModel {
        return GroupUIModel(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            hexColor = domainModel.colorHex,
            phrases = domainModel.phrases,
            isSelected = false
        )
    }
}