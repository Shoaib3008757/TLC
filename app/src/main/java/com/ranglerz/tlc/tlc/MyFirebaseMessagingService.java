package com.ranglerz.tlc.tlc;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.AddDriver;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ReportAccident;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Message.Notification;

/**
 * Created by Hafiz Adeel on 2/15/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    String PostID , PostMessage;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //onMessageReceived will be called when ever you receive new message from server.. (app in background and foreground )
        Log.d("FCM", "From: " + remoteMessage.getFrom());

        if(remoteMessage.getNotification()!=null){
            Log.d("FCM", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }

//        PostID = remoteMessage.getData().get("post_id").toString();
        PostMessage = remoteMessage.getData().get("post_title").toString();

        if(remoteMessage.getData().containsKey("post_title")){
           // Log.d("Post ID",PostID);
            Log.d("Post Title",PostMessage);
            // eg. Server Send Structure data:{"post_id":"12345","post_title":"A Blog Post"}
        }


        sendNotification(PostMessage);

    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this , Notification.class);
        Bundle bundle = new Bundle();
        bundle.putString("notification", messageBody);
        intent.putExtras(bundle);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TLC APP")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}