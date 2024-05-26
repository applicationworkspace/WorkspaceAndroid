package com.workspaceandroid.data.mappers

import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.data.common.TIME_FORMAT_FULL_DATE_PATTERN
import com.workspaceandroid.data.common.TIME_FORMAT_RESPONSE_UTC_PATTERN
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.models.phrase.PhraseInput
import javax.inject.Inject

class PhrasesNetMapper @Inject constructor(private val timeHelper: ITimeHelper) :
    EntityMapper<PhraseNetDTO, PhraseModel> {

    override fun mapFromEntity(entity: PhraseNetDTO): PhraseModel {
        val createdAtTimeStamp =
            timeHelper.getLongFromString(entity.createdAt, TIME_FORMAT_RESPONSE_UTC_PATTERN)
        return PhraseModel(
            id = entity.phraseId ?: -1,
            createdAt = createdAtTimeStamp,
            formattedDate = timeHelper.convertToFormattedTime(
                createdAtTimeStamp,
                TIME_FORMAT_FULL_DATE_PATTERN
            ),
            text = entity.phraseText.orEmpty(),
            translation = entity.translation.orEmpty(),
            imgUrl = entity.phraseImgUrl.orEmpty(),
            examples = entity.phraseExamples ?: emptyList(),
            definition = entity.phraseDefinition.orEmpty(),
            isDone = (entity.repeatCount ?: 0) > 3,
            isExpanded = false
        )
    }

    fun phraseInputToDto(input: PhraseInput): PhraseNetDTO { //TODO create request DTO
        return PhraseNetDTO(
            phraseText = input.text,
            phraseDefinition = input.definition,
            phraseExamples = input.examples.values.toList(),
            createdAt = null,
            phraseUserId = null,
            phraseId = null,
            phraseImgUrl = null,
            translation = input.translation,
            repeatCount = 0
        )
    }
}