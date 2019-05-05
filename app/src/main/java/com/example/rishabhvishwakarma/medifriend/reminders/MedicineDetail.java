package com.example.rishabhvishwakarma.medifriend.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rishabhvishwakarma.medifriend.R;

public class MedicineDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, new MedicineDetail_fragment())
                    .commit();
        }
    }
}
