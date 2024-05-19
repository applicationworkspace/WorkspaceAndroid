package com.workspaceandroid.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.workspaceandroid.MainActivity
import com.workspaceandroid.R
import com.workspaceandroid.domain.interactors.NotificationInteractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

const val NOTIFICATION_ID = 1

const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationInteractor: NotificationInteractor

    override fun onReceive(context: Context, intent: Intent?) = runAsync {

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val userPhrase = notificationInteractor.getUserNotificationPhrase()

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = NOTIFICATION_CHANNEL_ID,
            title = userPhrase.text,
            bodyText = "Check new phrase",
            expandedBodyText = userPhrase.definition
        )
//        RemindersManager.startReminder(context.applicationContext)
    }

    private fun BroadcastReceiver.runAsync(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob()).launch(context) {
            try {
                block()
            } finally {
                pendingResult.finish()
            }
        }
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
    title: String,
    bodyText: String,
    expandedBodyText: String,
) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle(title)
        .setContentText(bodyText)
        .setSmallIcon(R.drawable.ic_launcher)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(expandedBodyText)
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}