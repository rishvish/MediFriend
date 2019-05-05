package com.example.rishabhvishwakarma.medifriend.reminders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.rishabhvishwakarma.medifriend.R;

public class ReminderActivity extends AppCompatActivity {

    ImageButton AddButton,AllReminderButton,AllTodayReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        AddButton = findViewById(R.id.AddReportsButton);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(ReminderActivity.this,
                        AddReminder.class);
                startActivity(myIntent);
            }
        });

        AllReminderButton = (ImageButton) findViewById(R.id.PillsButton);
        AllReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent = new Intent(ReminderActivity.this,
                        AllReminders.class);
                startActivity(newIntent);
            }
        });
        AllTodayReminder = (ImageButton) findViewById(R.id.DeleteReportsButton);
        AllTodayReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent = new Intent(ReminderActivity.this,
                        AllReminderWithTime.class);
                startActivity(newIntent);
            }
        });

    }
}
