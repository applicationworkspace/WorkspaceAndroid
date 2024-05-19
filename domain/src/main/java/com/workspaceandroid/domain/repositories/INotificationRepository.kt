package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.phrase.Phrase

interface INotificationRepository {
    suspend fun fetchUserPhraseForNotification(): Phrase
}