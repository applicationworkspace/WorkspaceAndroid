package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.phrase.PhraseModel

interface INotificationRepository {
    suspend fun fetchUserPhraseForNotification(): PhraseModel
}