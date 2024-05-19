package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.repositories.ICollectionRepository
import com.workspaceandroid.domain.repositories.INotificationRepository

class NotificationRepository(
    private val netSource: IPhrasesNetSource,
    private val phrasesNetMapper: PhrasesNetMapper
): INotificationRepository {

    override suspend fun fetchUserPhraseForNotification(): Phrase {
        return netSource.fetchUserPhrases().run(phrasesNetMapper::fromEntityList).random()
    }

}