package com.workspaceandroid.data.api.service

import com.workspaceandroid.data.dto.phrases.GroupModelNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseRepeatStatusRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApiService {

    @GET("/api/phrases")
    suspend fun getUserPhrases(): List<PhraseNetDTO>

    @POST("/api/collections/{collectionId}/phrase")
    suspend fun createUserPhrase(
        @Path("collectionId") collectionId: Long,
        @Body model: PhraseNetDTO
    )

    @POST("/api/collections")
    suspend fun createUserGroup(@Body model: GroupModelNetDTO): GroupModelNetDTO

    @DELETE("/api/phrases")
    suspend fun deleteUserPhrase(@Query("phraseId") phraseId: Long)

    @GET("/api/collections")
    suspend fun getUserGroups(): List<GroupModelNetDTO>

    @DELETE("/api/collections")
    suspend fun deleteUserGroup(@Query("groupId") groupId: Long)

    @GET("/api/phrases/phrases-game")
    suspend fun getPhrasesForGame(): List<PhraseNetDTO>

    @GET("/api/phrases/prediction")
    suspend fun loadPrediction(@Query("phraseText") phraseText: String): PhraseNetDTO

    @POST("/api/phrases/status")
    suspend fun updatePhraseRepeatStatus(
        @Body phraseRepeatStatusRequestDTO: PhraseRepeatStatusRequestDTO
    )

}