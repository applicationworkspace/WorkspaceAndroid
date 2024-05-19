package defaultModels

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