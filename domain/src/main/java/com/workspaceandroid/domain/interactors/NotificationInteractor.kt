package com.workspaceandroid.domain.interactors

import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.repositories.ICollectionRepository
import com.workspaceandroid.domain.repositories.INotificationRepository
import javax.inject.Inject

class NotificationInteractor @Inject constructor(
    private val notificationRepository: INotificationRepository
) {

    suspend fun getUserNotificationPhrase(): Phrase {
        return notificationRepository.fetchUserPhraseForNotification()
    }
}