package com.example.rishabhvishwakarma.medifriend.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rishabhvishwakarma.medifriend.R;

public class DeleteMedicine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_medicine);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.delete_container, new AllMedicines_Fragment())
                    .commit();
        }
    }
}
