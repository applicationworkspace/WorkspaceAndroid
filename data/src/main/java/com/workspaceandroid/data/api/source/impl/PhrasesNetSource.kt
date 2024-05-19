package com.workspaceandroid.data.api.source.impl

import com.workspaceandroid.data.api.RefreshTokenService
import com.workspaceandroid.data.api.service.NetworkApiService
import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.api.source.base.BaseNetSource
import com.workspaceandroid.data.dto.phrases.CollectionNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseRepeatStatusRequestDTO

internal class PhrasesNetSource(
    networkApiService: NetworkApiService,
    refreshTokenService: RefreshTokenService,
) : BaseNetSource<NetworkApiService>(networkApiService, refreshTokenService), IPhrasesNetSource {

    override suspend fun fetchUserPhrases(): List<PhraseNetDTO> {
        return performRequest { getUserPhrases() }
    }

    override suspend fun addUserPhrase(collectionId: Long, userPhrase: PhraseNetDTO) {
        return performRequest { createUserPhrase(collectionId, userPhrase) }
    }

    override suspend fun removePhrase(phraseId: Long) {
        performRequest { deleteUserPhrase(phraseId) }
    }

    override suspend fun getPhrasePrediction(phraseText: String): PhraseNetDTO { //TODO refactor
        return try {
            performRequest {
                loadPrediction(phraseText)
            }
        } catch (e: Exception) {
             PhraseNetDTO("123", "", "translation", 1L, "image", emptyList(), "definition", 1L, 0)
        }
    }

    override suspend fun getUserCollections(): List<CollectionNetDTO> {
        return performRequest { getUserCollections() }
    }

    override suspend fun getPhrasesForGame(): List<PhraseNetDTO> {
        return performRequest { getPhrasesForGame() }
    }

    override suspend fun updatePhraseRepeatStatus(phraseId: Long, isKnown: Boolean) {
        performRequest {
            updatePhraseRepeatStatus(PhraseRepeatStatusRequestDTO(phraseId, isKnown))
        }
    }

}