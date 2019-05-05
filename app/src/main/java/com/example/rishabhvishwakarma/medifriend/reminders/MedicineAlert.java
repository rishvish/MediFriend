package com.example.rishabhvishwakarma.medifriend.reminders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rishabhvishwakarma.medifriend.R;

public class MedicineAlert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_alert);
        Intent intent=getIntent();
        String name=intent.getStringExtra("Medicine_name");
        String doses=intent.getStringExtra("Dosage");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this.getBaseContext());
        String displayNotificationsKey = getString(R.string.pref_enable_Vibration_key);
        final boolean displayNotifications = prefs.getBoolean(displayNotificationsKey,
                Boolean.parseBoolean(getString(R.string.pref_enable_Vibration_default)));


        final Vibrator vibrator;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(displayNotifications) {

            vibrator.vibrate(new long[]{0, 200, 0}, 0);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Medication Reminder");
        alertDialogBuilder
                .setMessage("Time for your "+ name +" Medicine.\nTake "+doses+" pills.")
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        stopService(getIntent());
                        Ringtone r=AlarmReceiver.ringtone;
                        if(displayNotifications) {
                            vibrator.cancel();}
                        r.stop();
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
