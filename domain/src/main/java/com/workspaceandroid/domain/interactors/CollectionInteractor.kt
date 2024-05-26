package com.workspaceandroid.domain.interactors

import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.domain.models.phrase.GroupModel
import com.workspaceandroid.domain.repositories.ICollectionRepository
import javax.inject.Inject

class CollectionInteractor @Inject constructor(
    private val collectionRepository: ICollectionRepository
) {

    suspend fun getUserPhrases(): List<PhraseModel> {
        return collectionRepository.fetchUserPhrases()
    }

    suspend fun addUserPhrase(phrase: PhraseInput) {
        return collectionRepository.addUserPhrase(phrase)
    }

    suspend fun removePhrase(phraseId: Long) {
        return collectionRepository.removePhrase(phraseId)
    }

    suspend fun getPhrasePrediction(phraseText: String): PhraseModel {
        return collectionRepository.getPhrasePrediction(phraseText)
    }

    suspend fun getPhrasesForGame(): List<PhraseModel> {
        return collectionRepository.getPhrasesForGame()
    }

    suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean) {
        collectionRepository.updatePhraseRepeatStatus(phraseId, isKnown)
    }


    suspend fun getUserGroups(): List<GroupModel> {
        return collectionRepository.getUserGroups()
    }

    suspend fun addUserGroup(userGroup: GroupModel): GroupModel {
        return collectionRepository.addUserGroup(userGroup)
    }

    suspend fun deleteUserGroup(id: Long) {
        return collectionRepository.deleteUserGroup(id)
    }
}