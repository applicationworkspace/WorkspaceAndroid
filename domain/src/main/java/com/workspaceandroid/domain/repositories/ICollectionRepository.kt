package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.domain.models.phrase.GroupModel

interface ICollectionRepository {
    suspend fun fetchUserPhrases(): List<PhraseModel>
    suspend fun addUserPhrase(phrase: PhraseInput)
    suspend fun removePhrase(phraseId: Long)
    suspend fun getPhrasePrediction(phraseText: String): PhraseModel
    suspend fun getUserGroups(): List<GroupModel>
    suspend fun addUserGroup(group: GroupModel)
    suspend fun deleteUserGroup(groupId: Long)
    suspend fun getPhrasesForGame(): List<PhraseModel>
    suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean)
}