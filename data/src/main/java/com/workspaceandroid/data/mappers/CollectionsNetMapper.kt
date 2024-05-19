package com.workspaceandroid.data.mappers

import com.workspaceandroid.data.dto.phrases.CollectionNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.UserCollection
import javax.inject.Inject

class CollectionsNetMapper @Inject constructor(
    private val phrasesNetMapper: PhrasesNetMapper,
) : EntityMapper<CollectionNetDTO, UserCollection> {

    override fun mapFromEntity(entity: CollectionNetDTO): UserCollection {
        return UserCollection(
            id = entity.id ?: -1,
            color = entity.color ?: 16777215,
            name = entity.name.orEmpty(),
            description = entity.description.orEmpty(),
            phrases = phrasesNetMapper.fromEntityList(entity.phrases.orEmpty())
        )
    }

    fun fromEntityList(initial: List<CollectionNetDTO>): List<UserCollection> {
        return initial.map { mapFromEntity(it) }
    }

}