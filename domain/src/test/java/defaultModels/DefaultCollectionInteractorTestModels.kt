package defaultModels

import com.workspaceandroid.domain.models.phrase.PhraseModel

fun defaultPhraseModel(): PhraseModel =
    PhraseModel(
        id = 1,
        createdAt = 1L,
        formattedDate = "date",
        text = "text",
        imgUrl = "imageUrl",
        examples = emptyList(),
        definition = "definition"
    )