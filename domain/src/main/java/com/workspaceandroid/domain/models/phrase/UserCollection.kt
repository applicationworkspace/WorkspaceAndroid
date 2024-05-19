package com.workspaceandroid.domain.models.phrase

data class UserCollection(
    val id: Long,
    val color: Int,
    val name: String,
    val description: String,
    val phrases: List<Phrase>
)