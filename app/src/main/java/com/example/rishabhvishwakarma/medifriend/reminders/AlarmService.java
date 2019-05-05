package com.example.rishabhvishwakarma.medifriend.reminders;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.rishabhvishwakarma.medifriend.R;

import static android.content.Intent.getIntent;

public class AlarmService extends JobIntentService {
    private NotificationManager alarmNotificationManager;
    static final int SERVICE_JOB_ID = 50;
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    public void onHandleIntent(Intent intent) {
        createNotificationChannel();
        String name=AddReminder.medicine_name;
        String doses=AddReminder.dosage;
        sendNotification("Time for your "+ name +" Medicine.\nTake "+doses+" pills.");
    }
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AlarmService.class, SERVICE_JOB_ID, work);
    }
    private void sendNotification(String msg) {
        Log.i("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AllReminderWithTime.class), 0);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(
                this,"MedicineAlarmChannel").setContentTitle("Medication Reminder:").setSmallIcon(R.drawable.alarm)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");

        Intent intent;
        String name=AddReminder.medicine_name;
        String doses=AddReminder.dosage;
        intent = new Intent(this, MedicineAlert.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("AlarmService", "sendNotification: "+name);
        intent.putExtra("Medicine_name",name);
        intent.putExtra("Dosage",doses);
        startActivity(intent);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm";
            String description = "Medicine Alarm Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MedicineAlarmChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
