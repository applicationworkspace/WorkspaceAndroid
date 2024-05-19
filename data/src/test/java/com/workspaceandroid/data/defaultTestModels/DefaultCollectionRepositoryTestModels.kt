package com.workspaceandroid.data.defaultTestModels

import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.domain.models.phrase.Phrase

fun defaultPhraseModel(): Phrase =
    Phrase(
        id = 1,
        createdAt = 1L,
        formattedDate = "date",
        text = "text",
        imgUrl = "imageUrl",
        examples = emptyList(),
        definition = "definition"
    )

fun defaultPhraseNetDTOModel(): PhraseNetDTO =
    PhraseNetDTO(
        createdAt = "createdAt",
        phraseText = "phraseText",
        phraseId = 1,
        phraseImgUrl = "imageUrl",
        phraseExamples = emptyList(),
        phraseDefinition = "definition",
        phraseUserId = 1
    )