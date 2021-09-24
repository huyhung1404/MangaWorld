package com.example.mangaworld.Main.CommunityFragment.MyNotificationFragment.FirebasePushNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.mangaworld.Interface.IReceiveNotification;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static IReceiveNotification iReceiveNotification = null;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String,String> stringMap = remoteMessage.getData();
        sendNotification(stringMap.get("title"),stringMap.get("content"));
        if(iReceiveNotification != null){
            iReceiveNotification.receiveNotification();
        }
    }

    private void sendNotification(String title, String content) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,MyApplication.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager != null){
            notificationManager.notify(1,notification);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
