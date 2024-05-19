package com.workspaceandroid.data.dto.phrases

import com.google.gson.annotations.SerializedName

data class PhraseRepeatStatusRequestDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("isKnown")
    val isKnown: Boolean
)
