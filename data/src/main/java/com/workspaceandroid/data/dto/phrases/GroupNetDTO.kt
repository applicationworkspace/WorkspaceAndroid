package com.workspaceandroid.data.dto.phrases

import com.google.gson.annotations.SerializedName

data class GroupNetDTO(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("colorHex")
    val colorHex: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("phrases")
    val phrases: List<PhraseNetDTO>?
)