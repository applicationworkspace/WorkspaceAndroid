package com.workspaceandroid.data.api.service

import com.workspaceandroid.data.dto.phrases.CollectionNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseRepeatStatusRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApiService {

    @GET("phrases")
    suspend fun getUserPhrases(): List<PhraseNetDTO>

    @POST("collections/{collectionId}/phrase")
    suspend fun createUserPhrase(
        @Path("collectionId") collectionId: Long,
        @Body model: PhraseNetDTO
    )

    @DELETE("phrases")
    suspend fun deleteUserPhrase(@Query("phraseId") phraseId: Long)

    @GET("collections")
    suspend fun getUserCollections(): List<CollectionNetDTO>

    @GET("phrases/phrases-game")
    suspend fun getPhrasesForGame(): List<PhraseNetDTO>

    @GET("phrases/prediction")
    suspend fun loadPrediction(@Query("phraseText") phraseText: String): PhraseNetDTO

    @POST("phrases/status")
    suspend fun updatePhraseRepeatStatus(
        @Body phraseRepeatStatusRequestDTO: PhraseRepeatStatusRequestDTO
    )

}