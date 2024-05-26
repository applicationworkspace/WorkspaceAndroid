package com.workspaceandroid.model

import com.workspaceandroid.domain.models.phrase.PhraseModel

data class GroupUIModel(
    val id: Long,
    val name: String,
    val description: String,
    val hexColor: String,
    val phrases: List<PhraseModel>,

    val isSelected: Boolean
)
