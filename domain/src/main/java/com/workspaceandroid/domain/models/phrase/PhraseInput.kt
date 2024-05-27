package com.workspaceandroid.domain.models.phrase

data class PhraseInput(
    var text: String = "",
    var examples: MutableMap<Int, String> = mutableMapOf(),
    var definition: String = "",
    var translation: String = "",
    var selectedCollectionId: Long = -1 //TODO nullable
)