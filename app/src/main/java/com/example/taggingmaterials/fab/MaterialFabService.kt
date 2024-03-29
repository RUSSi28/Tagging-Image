package com.example.taggingmaterials.fab

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.taggingmaterials.R
import com.torrydo.floatingbubbleview.helper.ViewHelper
import com.torrydo.floatingbubbleview.service.expandable.BubbleBuilder
import com.torrydo.floatingbubbleview.service.expandable.ExpandableBubbleService
import com.torrydo.floatingbubbleview.service.expandable.ExpandedBubbleBuilder

class MaterialFabService : ExpandableBubbleService() {

    override fun onCreate() {
        super.onCreate()
        minimize()


        // 通知チャネルを作成する
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = CHANNEL_DESCRIPTION
            setAllowBubbles(true) // このチャネルでバブルを許可
        }

        // システムの通知マネージャを取得する
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 通知マネージャにチャネルを登録
        notificationManager.createNotificationChannel(channel)
        val bubbleMetadata = Notification.BubbleMetadata.Builder().build()

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setBubbleMetadata(bubbleMetadata)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        // 通知ビルダーを使用してNotificationオブジェクトを作成
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_background) // 通知のアイコン
            setContentTitle("Notification Title") // 通知のタイトル
            setContentText("Notification Content") // 通知の内容
            setPriority(NotificationCompat.PRIORITY_DEFAULT) // 通知の優先度
        }
    }

    override fun configBubble(): BubbleBuilder? {
        val imageView = ViewHelper.fromDrawable(this, R.drawable.ic_launcher_background, 60, 60)

        return BubbleBuilder(this)
            .bubbleView(imageView)

            // set start location for the bubble, (x=0, y=0) is the top-left
            .startLocation(100, 100)    // in dp
            .startLocationPx(100, 100)  // in px
    }

    override fun configExpandedBubble(): ExpandedBubbleBuilder? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "MaterialFab"
        private val CHANNEL_NAME : CharSequence = "MaterialFab"
        private const val CHANNEL_DESCRIPTION = ""
    }
}