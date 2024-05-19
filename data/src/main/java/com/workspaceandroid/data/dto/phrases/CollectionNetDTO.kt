package com.workspaceandroid.data.dto.phrases

import com.google.gson.annotations.SerializedName

data class CollectionNetDTO(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("color")
    val color: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("phrases")
    val phrases: List<PhraseNetDTO>?
)