package com.workspaceandroid.data.dto.phrases

import com.google.gson.annotations.SerializedName

data class PhraseNetDTO(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("text")
    val phraseText: String?,
    @SerializedName("translation")
    val translation: String?,
    @SerializedName("id")
    val phraseId: Long?,
    @SerializedName("imageUrl")
    val phraseImgUrl: String?,
    @SerializedName("examples")
    val phraseExamples: List<String>?,
    @SerializedName("definition")
    val phraseDefinition: String?,
    @SerializedName("userId")
    val phraseUserId: Long?,
    @SerializedName("repeatCount")
    val repeatCount: Long?
)