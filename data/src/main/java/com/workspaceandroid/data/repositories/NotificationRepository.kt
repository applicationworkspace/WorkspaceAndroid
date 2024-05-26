package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.ICollectionNetSource
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.repositories.INotificationRepository

class NotificationRepository(
    private val netSource: ICollectionNetSource,
    private val phrasesNetMapper: PhrasesNetMapper
): INotificationRepository {

    override suspend fun fetchUserPhraseForNotification(): PhraseModel {
        return netSource.fetchUserPhrases().run(phrasesNetMapper::mapListFrom).random()
    }

}