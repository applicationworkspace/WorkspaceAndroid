package com.workspaceandroid.domain.models.phrase

data class GroupModel(
    val id: Long,
    val colorHex: String,
    val name: String,
    val description: String,
    val phrases: List<PhraseModel>
)