package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.ICollectionNetSource
import com.workspaceandroid.data.mappers.GroupNetMapper
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.domain.models.phrase.GroupModel
import com.workspaceandroid.domain.repositories.ICollectionRepository

class CollectionRepository(
    private val netSource: ICollectionNetSource,
    private val phrasesNetMapper: PhrasesNetMapper,
    private val groupNetMapper: GroupNetMapper
): ICollectionRepository {

    override suspend fun fetchUserPhrases(): List<PhraseModel> {
        val userPhrases = netSource.fetchUserPhrases()
        return userPhrases.run(phrasesNetMapper::mapListFrom)
    }

    override suspend fun addUserPhrase(phrase: PhraseInput) {
        netSource.addUserPhrase(phrase.selectedCollectionId, phrasesNetMapper.phraseInputToDto(phrase))
    }

    override suspend fun removePhrase(phraseId: Long) {
        netSource.removePhrase(phraseId)
    }

    override suspend fun getPhrasePrediction(phraseText: String): PhraseModel {
        return netSource.getPhrasePrediction(phraseText).run(phrasesNetMapper::mapFromEntity)
    }

    override suspend fun getUserGroups(): List<GroupModel> {
        return netSource.getUserGroups().run(groupNetMapper::mapListFrom)
    }

    override suspend fun addUserGroup(group: GroupModel) {
        netSource.addUserGroup(groupNetMapper.mapToEntity(group))
    }

    override suspend fun deleteUserGroup(groupId: Long) {
        netSource.deleteUserGroup(groupId)
    }

    override suspend fun getPhrasesForGame(): List<PhraseModel> {
        return netSource.getPhrasesForGame().run(phrasesNetMapper::mapListFrom)
    }

    override suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean) {
        netSource.updatePhraseRepeatStatus(phraseId, isKnown)
    }

}