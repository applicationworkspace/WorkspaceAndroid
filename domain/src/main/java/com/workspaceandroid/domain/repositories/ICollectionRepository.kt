package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.domain.models.phrase.UserCollection

interface ICollectionRepository {
    suspend fun fetchUserPhrases(): List<Phrase>
    suspend fun addUserPhrase(phrase: PhraseInput)
    suspend fun removePhrase(phraseId: Long)
    suspend fun getPhrasePrediction(phraseText: String): Phrase
    suspend fun getUserCollections(): List<UserCollection>
    suspend fun getPhrasesForGame(): List<Phrase>
    suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean)
}