package com.workspaceandroid.domain.interactors

import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.domain.models.phrase.UserCollection
import com.workspaceandroid.domain.repositories.ICollectionRepository
import javax.inject.Inject

class CollectionInteractor @Inject constructor(
    private val collectionRepository: ICollectionRepository
) {

    suspend fun getUserPhrases(): List<Phrase> {
        return collectionRepository.fetchUserPhrases()
    }

    suspend fun addUserPhrase(phrase: PhraseInput) {
        return collectionRepository.addUserPhrase(phrase)
    }

    suspend fun removePhrase(phraseId: Long) {
        return collectionRepository.removePhrase(phraseId)
    }

    suspend fun getUserCollections(): List<UserCollection> {
        return collectionRepository.getUserCollections()
    }

    suspend fun getPhrasePrediction(phraseText: String): Phrase {
        return collectionRepository.getPhrasePrediction(phraseText)
    }

    suspend fun getPhrasesForGame(): List<Phrase> {
        return collectionRepository.getPhrasesForGame()
    }

    suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean) {
        collectionRepository.updatePhraseRepeatStatus(phraseId, isKnown)
    }
}