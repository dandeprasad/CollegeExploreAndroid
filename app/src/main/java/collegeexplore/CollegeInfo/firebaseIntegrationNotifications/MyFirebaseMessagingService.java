/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package collegeexplore.CollegeInfo.firebaseIntegrationNotifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import collegeexplore.CollegeInfo.FestsCollegesWorkspace.TabsHeaderActivity;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.R;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Bitmap bitmap,bitmap1,bitmap2;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

    
        JSONObject valuescame = null;
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        try {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

          //  sendNotification();

//To get a Bitmap image from the URL received
          //  bitmap = getBitmapfromUrl(imageUri);

         Map<String,String> val=  remoteMessage.getData();

                //JSONObject values = new JSONObject((String) val.get("0"));
            for (Map.Entry<String, String> entry : val.entrySet())
            {
                 valuescame = new JSONObject(entry.getValue());

                System.out.println(entry.getKey() + "/" + entry.getValue());
            }

      String app_to_open = valuescame.get("APP_KEY").toString();
            String messagebody = valuescame.get("MESSAGE_DETAIL").toString();
            String title = valuescame.get("TITLE").toString();

            if(app_to_open.equalsIgnoreCase("BASIC")) {

                sendNotification(title,messagebody);
            }


            if(app_to_open.equalsIgnoreCase("FESTS")) {
                JSONObject dataentries = new JSONObject(valuescame.get("data").toString());
                   String clg_id=  dataentries.get("CLG_ID").toString();
                String festimage=  dataentries.get("BANNER_LOGO").toString();
                String collegename=  dataentries.get("FEST_CLG").toString();
                bitmap2 = getBitmapfromUrl(festimage);
                sendNotificationfests(bitmap2, app_to_open , messagebody,title,clg_id,festimage,collegename);
            }


            if(app_to_open.equalsIgnoreCase("EXAMS")) {
                JSONObject dataentries = new JSONObject(valuescame.get("data").toString());
                String idtosend=  dataentries.get("EXAM_ID").toString();
                String imageUri = dataentries.get("DATA_IMAGE").toString();
               // String imageUri1 = dataentries.get("DATA_IMAGE_LOGO").toString();
                bitmap = getBitmapfromUrl(imageUri);
              //  bitmap1 = getBitmapfromUrl(imageUri);
                sendNotification( bitmap1, bitmap,app_to_open ,idtosend, messagebody,title);
            }
            if(app_to_open.equalsIgnoreCase("ADMISSIONS")) {
                JSONObject dataentries = new JSONObject(valuescame.get("data").toString());
                String imageUri = dataentries.get("DATA_IMAGE").toString();
                // String imageUri1 = dataentries.get("DATA_IMAGE_LOGO").toString();
                bitmap = getBitmapfromUrl(imageUri);
                //  bitmap1 = getBitmapfromUrl(imageUri);


                sendNotificationadmis(bitmap, app_to_open , messagebody,title);
            }
            if(app_to_open.equalsIgnoreCase("NEWS_WORKSPACE")) {
                JSONObject dataentries = new JSONObject(valuescame.get("data").toString());
                String imageUri = dataentries.get("DATA_IMAGE").toString();
                String NEWS_HEADER = dataentries.get("NEWS_HEADER").toString();
                String NEWS_DETAILS = dataentries.get("NEWS_DETAILS").toString();
                String NEWS_IMAGE = dataentries.get("NEWS_IMAGE").toString();
                // String imageUri1 = dataentries.get("DATA_IMAGE_LOGO").toString();
                bitmap = getBitmapfromUrl(imageUri);
                //  bitmap1 = getBitmapfromUrl(imageUri);


                sendNotificationOnlynews(bitmap, app_to_open , messagebody,title,NEWS_HEADER,NEWS_DETAILS,NEWS_IMAGE);
            }


/*            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/

        }

/*        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/
           if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Dande Message Notification Body: " + remoteMessage.getNotification().getBody());





            }
        }catch (Exception e) {
        e.printStackTrace();
    }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *

     */
    private void sendNotificationadmis(Bitmap image, String app_to_open, String messageBody, String title) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i2 = null;
/*
        Intent intent = new Intent(this, UserNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.putExtra("contextid",contextid);
       intent.putExtra("apptoopen",app_to_open);
*/


        i2 = new Intent(this, HomeActivity.class);
        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i2.putExtra("app_to_open", app_to_open);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel=new NotificationChannel("dandeking_notification","danden_channel",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i2,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"dandeking_notification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setLargeIcon(image)
                .setChannelId("dandeking_notification")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setContentText(messageBody)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.app_theme))
                .setSound(defaultSoundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());;



        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE) /* ID of notification */, notificationBuilder.build());
    }
    private void sendNotificationOnlynews(Bitmap image, String app_to_open, String messageBody, String title,String NEWS_HEADER,String NEWS_DETAILS,String NEWS_IMAGE) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i2 = null;
/*
        Intent intent = new Intent(this, UserNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.putExtra("contextid",contextid);
       intent.putExtra("apptoopen",app_to_open);
*/



        i2 = new Intent(this, HomeActivity.class);
        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        i2.putExtra("NEWS_HEADER", NEWS_HEADER);
        i2.putExtra("NEWS_DETAILS", NEWS_DETAILS);
        i2.putExtra("app_to_open", app_to_open);
        i2.putExtra("NEWS_IMAGE", NEWS_IMAGE);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel=new NotificationChannel("dandeking_notification"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE),"danden_channel"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE),NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("description"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE));
            notificationChannel.setName("Channel Name"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE));
            notificationChannel.setLightColor(Color.GRAY);
            notificationChannel.enableLights(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(defaultSoundUri, audioAttributes);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i2,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"dandeking_notification"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setLargeIcon(image)
                .setChannelId("dandeking_notification"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setContentText(messageBody)
                .setVisibility(VISIBILITY_PUBLIC)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.app_theme))
                .setSound(defaultSoundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());;



        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE) /* ID of notification */, notificationBuilder.build());
    }
   private void sendNotification(Bitmap logo,Bitmap image,String app_to_open, String contextid, String messageBody, String title) {

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i2 = null;
/*
        Intent intent = new Intent(this, UserNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.putExtra("contextid",contextid);
       intent.putExtra("apptoopen",app_to_open);
*/


        i2 = new Intent(this, HomeActivity.class);
        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i2.putExtra("EXAM_ID", contextid);
        i2.putExtra("app_to_open", app_to_open);
       Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel=new NotificationChannel("dandeking_notification","danden_channel",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            notificationChannel.setLightColor(Color.GRAY);
            notificationChannel.enableLights(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(defaultSoundUri, audioAttributes);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i2,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"dandeking_notification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setLargeIcon(image)
                .setChannelId("dandeking_notification")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setContentText(messageBody)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.app_theme))
                .setSound(defaultSoundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());;



        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE) /* ID of notification */, notificationBuilder.build());
    }












    private void sendNotification(String title, String messagecont) {

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i2 = null;
/*
        Intent intent = new Intent(this, UserNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.putExtra("contextid",contextid);
       intent.putExtra("apptoopen",app_to_open);
*/


        i2 = new Intent(this, HomeActivity.class);
        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel=new NotificationChannel("dandeking_notification","danden_channel",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            notificationManager.createNotificationChannel(notificationChannel);
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i2,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"dandeking_notification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)

                .setChannelId("dandeking_notification")
                .setContentText(messagecont)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.app_theme))
                .setSound(defaultSoundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());;



        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE) /* ID of notification */, notificationBuilder.build());

    }


    private void sendNotificationfests(Bitmap image,String app_to_open, String messageBody, String title,String clg_id,String festimage,String  collegename) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i2 = null;
/*
        Intent intent = new Intent(this, UserNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent.putExtra("contextid",contextid);
       intent.putExtra("apptoopen",app_to_open);
*/


        i2 = new Intent(this, TabsHeaderActivity.class);
        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        i2.putExtra("clg_id", clg_id);
        i2.putExtra("festimage", festimage);
        i2.putExtra("collegename", collegename);




     //   i2 = new Intent(this, HomeActivity.class);
       // i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel=new NotificationChannel("dandeking_notification"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE),"danden_channel",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("description"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE));
            notificationChannel.setName("Channel Name"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE));
            notificationManager.createNotificationChannel(notificationChannel);
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i2,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"dandeking_notification"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setLargeIcon(image)
                .setChannelId("dandeking_notification"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setContentText(messageBody)

                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.app_theme))
                .setSound(defaultSoundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setVisibility(VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());;



        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE) /* ID of notification */, notificationBuilder.build());

    }





    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

}
