package com.example.rishabhvishwakarma.medifriend.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rishabhvishwakarma.medifriend.R;

public class AllReminders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reminders);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Container, new AllMedicines_Fragment())
                    .commit();
        }
    }
}
