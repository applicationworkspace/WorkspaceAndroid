package com.workspaceandroid.data.api.source.impl

import com.workspaceandroid.data.api.RefreshTokenService
import com.workspaceandroid.data.api.service.NetworkApiService
import com.workspaceandroid.data.api.source.ICollectionNetSource
import com.workspaceandroid.data.api.source.base.BaseNetSource
import com.workspaceandroid.data.dto.phrases.GroupModelNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseRepeatStatusRequestDTO

internal class CollectionNetSource(
    networkApiService: NetworkApiService,
    refreshTokenService: RefreshTokenService,
) : BaseNetSource<NetworkApiService>(networkApiService, refreshTokenService), ICollectionNetSource {

    override suspend fun fetchUserPhrases(): List<PhraseNetDTO> {
        return performRequest { getUserPhrases() }
    }

    override suspend fun addUserPhrase(collectionId: Long, userPhrase: PhraseNetDTO) {
        return performRequest { createUserPhrase(collectionId, userPhrase) }
    }

    override suspend fun addUserGroup(group: GroupModelNetDTO): GroupModelNetDTO {
        return performRequest { createUserGroup(group) }
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

    override suspend fun getUserGroups(): List<GroupModelNetDTO> {
        return performRequest { getUserGroups() }
    }

    override suspend fun deleteUserGroup(groupId: Long) {
        performRequest { deleteUserGroup(groupId) }
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