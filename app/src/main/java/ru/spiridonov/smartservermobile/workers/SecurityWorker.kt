package ru.spiridonov.smartservermobile.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ru.spiridonov.smartservermobile.R
import ru.spiridonov.smartservermobile.domain.usecases.user.GetAccessTokenUseCase
import ru.spiridonov.smartservermobile.presentation.MainActivity
import javax.inject.Inject

class SecurityWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : CoroutineWorker
    (context, workerParameters) {

    override suspend fun doWork(): Result {
        val token = getAccessTokenUseCase.invoke() ?: return Result.retry()
        createNotificationChannel(context)
        val client = HttpClient(CIO) {
            install(WebSockets) {
                pingInterval = 1500
            }
            install(Auth) {
                bearer {
                    loadTokens { BearerTokens(token, token) }
                }
            }

        }
        runBlocking(Dispatchers.Main) {
            client.webSocket(
                method = HttpMethod.Get,
                host = "climat.protesys.ru",
                port = 80,
                path = "/security_websocket"
            ) {
                while (true) {
                    incoming.receive() as? Frame.Text ?: continue
                    createNotification(context)

                }
            }
        }

        return Result.retry()
    }


    private fun createNotification(context: Context) {
        val title = "Контроль серверной"
        val message = "Обнаружено нарушение периметра безопасности"
        val intent = Intent(context, MainActivity::class.java)
        val color = Color.RED
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setColor(color)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    1000,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val notificationId = 1
            NotificationManagerCompat.from(context).notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }


    companion object {
        const val WORK_NAME = "SecurityWorker"
        private const val CHANNEL_ID = "01"
        private const val CHANNEL_NAME = "Уведомления о нарушении периметра безопасности"
        fun makeRequest() =
            OneTimeWorkRequestBuilder<SecurityWorker>().build()
    }

    class Factory @Inject constructor(
        private val getAccessTokenUseCase: GetAccessTokenUseCase
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return SecurityWorker(context, workerParameters, getAccessTokenUseCase)
        }

    }
}