package com.example.rishabhvishwakarma.medifriend.reports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.rishabhvishwakarma.medifriend.Home;
import com.example.rishabhvishwakarma.medifriend.R;
import com.google.firebase.database.core.Repo;

public class ReportsActivity extends AppCompatActivity {

    ImageButton AddReportsButton,AllReportsButton,DeleteReportsButton;
    String email,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        uid=intent.getStringExtra("UID");
        AddReportsButton=findViewById(R.id.AddReportsButton);
        AllReportsButton=findViewById(R.id.ReportsButton);
        DeleteReportsButton=findViewById(R.id.DeleteReportsButton);

        AddReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReportsActivity.this,AddReports.class);
                intent.putExtra("email",email);
                intent.putExtra("UID",uid);
                startActivity(intent);
            }
        });
        AllReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReportsActivity.this,AllReports.class);
                intent.putExtra("email",email);
                intent.putExtra("UID",uid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(ReportsActivity.this,Home.class);
        startActivity(intent);
        this.finish();
    }

}
