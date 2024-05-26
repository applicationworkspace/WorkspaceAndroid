package com.workspaceandroid.domain.models.phrase

data class PhraseModel(
    val id: Long,
    val createdAt: Long,
    val formattedDate: String,
    val text: String,
    val translation: String,
    val definition: String,
    val imgUrl: String,
    val examples: List<String>,
    val isDone: Boolean,
    val repeatCount: Int,

    var isExpanded: Boolean
)