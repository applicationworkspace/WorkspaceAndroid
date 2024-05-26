package com.workspaceandroid.data.api.source

import com.workspaceandroid.data.dto.phrases.GroupModelNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.domain.models.phrase.GroupModel

interface ICollectionNetSource {
    suspend fun fetchUserPhrases(): List<PhraseNetDTO>
    suspend fun addUserPhrase(collectionId: Long, userPhrase: PhraseNetDTO)
    suspend fun addUserGroup(group: GroupModelNetDTO): GroupModelNetDTO
    suspend fun removePhrase(phraseId: Long)
    suspend fun getPhrasePrediction(phraseText: String): PhraseNetDTO
    suspend fun getUserGroups(): List<GroupModelNetDTO>
    suspend fun deleteUserGroup(groupId: Long)
    suspend fun getPhrasesForGame(): List<PhraseNetDTO>
    suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean)
}