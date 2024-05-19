package com.workspaceandroid.data.api.source

import com.workspaceandroid.data.dto.phrases.CollectionNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO

interface IPhrasesNetSource {
    suspend fun fetchUserPhrases(): List<PhraseNetDTO>
    suspend fun addUserPhrase(collectionId: Long, userPhrase: PhraseNetDTO)
    suspend fun removePhrase(phraseId: Long)
    suspend fun getPhrasePrediction(phraseText: String): PhraseNetDTO
    suspend fun getUserCollections(): List<CollectionNetDTO>
    suspend fun getPhrasesForGame(): List<PhraseNetDTO>
    suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean)
}