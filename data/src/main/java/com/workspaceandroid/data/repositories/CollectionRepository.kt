package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.mappers.CollectionsNetMapper
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.domain.models.phrase.UserCollection
import com.workspaceandroid.domain.repositories.ICollectionRepository

class CollectionRepository(
    private val netSource: IPhrasesNetSource,
    private val phrasesNetMapper: PhrasesNetMapper,
    private val collectionsNetMapper: CollectionsNetMapper
): ICollectionRepository {

    override suspend fun fetchUserPhrases(): List<Phrase> {
        val userPhrases = netSource.fetchUserPhrases()
        return userPhrases.run(phrasesNetMapper::fromEntityList)
    }

    override suspend fun addUserPhrase(phrase: PhraseInput) {
        netSource.addUserPhrase(phrase.selectedCollectionId, phrasesNetMapper.phraseInputToDto(phrase))
    }

    override suspend fun removePhrase(phraseId: Long) {
        netSource.removePhrase(phraseId)
    }

    override suspend fun getPhrasePrediction(phraseText: String): Phrase {
        return netSource.getPhrasePrediction(phraseText).run(phrasesNetMapper::mapFromEntity)
    }

    override suspend fun getUserCollections(): List<UserCollection> {
        return netSource.getUserCollections().run(collectionsNetMapper::fromEntityList)
    }

    override suspend fun getPhrasesForGame(): List<Phrase> {
        return netSource.getPhrasesForGame().run(phrasesNetMapper::fromEntityList)
    }

    override suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean) {
        netSource.updatePhraseRepeatStatus(phraseId, isKnown)
    }

}