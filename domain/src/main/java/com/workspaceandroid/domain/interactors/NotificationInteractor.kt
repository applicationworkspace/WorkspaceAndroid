package com.workspaceandroid.domain.interactors

import com.workspaceandroid.domain.models.phrase.PhraseModel
import com.workspaceandroid.domain.repositories.INotificationRepository
import javax.inject.Inject

class NotificationInteractor @Inject constructor(
    private val notificationRepository: INotificationRepository
) {

    suspend fun getUserNotificationPhrase(): PhraseModel {
        return notificationRepository.fetchUserPhraseForNotification()
    }
}