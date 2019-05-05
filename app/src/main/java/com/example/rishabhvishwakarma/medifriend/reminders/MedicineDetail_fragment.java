package com.example.rishabhvishwakarma.medifriend.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishabhvishwakarma.medifriend.R;

import java.util.Calendar;

public class MedicineDetail_fragment extends Fragment {
    public MedicineDetail_fragment() {
    }

    private static final String LOG_TAG = MedicineDetail_fragment.class.getSimpleName();
    private static final String FORECAST_SHARE_HASHTAG = " #MedicationReminder";
    private String sharestring;

    private ArrayAdapter<String> MedicineAdapter;
    Medicine Medicinebundle;
    String startdate,Docdate;
    String starttime;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle x = new Bundle();
        x = this.getArguments();
        if (x == null) {
            x = getActivity().getIntent().getExtras();
        }
        if (x != null) {
            Medicinebundle = (Medicine) x.getSerializable("Medicineobject_details");
        }


        View rootView = inflater.inflate(R.layout.detail_medicine, container, false);

        ((TextView) rootView.findViewById(R.id.MedName))
                .setText(Medicinebundle.getMedicineName());
        ((TextView) rootView.findViewById(R.id.MedType))
                .setText("Medicine Type");
        if(Medicinebundle.isMedicineType())
        {((TextView) rootView.findViewById(R.id.MedTypeedit))
                .setText("Oral Tablets");
            ImageView img= ((ImageView) rootView.findViewById(R.id.image_detail));
            img.setImageResource(R.drawable.pillsimage);


        }
        else
        {((TextView) rootView.findViewById(R.id.MedTypeedit))
                .setText("Injection");
            ImageView img= ((ImageView) rootView.findViewById(R.id.image_detail));
            img.setImageResource(R.drawable.injection);}

        ((TextView) rootView.findViewById(R.id.Dosagebt))
                .setText("Your Doses number");
        ((TextView) rootView.findViewById(R.id.Dosagebtedit))
                .setText(Medicinebundle.getDosage().toString());
        ((TextView) rootView.findViewById(R.id.freq))
                .setText("Frequency");
        if(Medicinebundle.getFrequency()==1) {
            ((TextView) rootView.findViewById(R.id.freqedit))
                    .setText("One Time");
        }
        else{
            ((TextView) rootView.findViewById(R.id.freqedit))
                    .setText("Every " + Medicinebundle.getFrequency().toString() + " Hours");
        }
        ((TextView) rootView.findViewById(R.id.startdate))
                .setText("Start Date");
        ((TextView) rootView.findViewById(R.id.DocAppdate))
                .setText("Next Doctor Appointment");
        ((TextView) rootView.findViewById(R.id.starttimex))
                .setText("Start Time");

        int m=Medicinebundle.Date.get(Calendar.DAY_OF_MONTH);
        startdate=(Medicinebundle.Date.get(Calendar.DAY_OF_MONTH)+"/"+(Medicinebundle.Date.get(Calendar.MONTH)+1)+"/"+Medicinebundle.Date.get(Calendar.YEAR));
        ((TextView) rootView.findViewById(R.id.startdateedit))
                .setText(startdate);

        Docdate=(Medicinebundle.DoctorApp.get(Calendar.DAY_OF_MONTH)+"/"+(Medicinebundle.DoctorApp.get(Calendar.MONTH)+1)+"/"+Medicinebundle.DoctorApp.get(Calendar.YEAR));
        ((TextView) rootView.findViewById(R.id.DocAppdateedit))
                .setText(Docdate);


        starttime=(Medicinebundle.Date.get(Calendar.HOUR_OF_DAY)+":"+Medicinebundle.Date.get(Calendar.MINUTE));
        ((TextView) rootView.findViewById(R.id.starttimeeditx))
                .setText(starttime);


        ImageButton DeleteBtn = (ImageButton) rootView.findViewById(R.id.deleteButton);
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Medicine MObj=Medicinebundle;
                //String Medicineitem = MedicineAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DeleteMedicine.class);// all medicine detail
                //.putExtra(Intent.EXTRA_TEXT, MObj);
                Bundle extras = new Bundle();
                extras.putSerializable("Medicineobject_delete", MObj);
                intent.putExtra("Medicineobject_delete", MObj);
                startActivity(intent);
                Toast.makeText(getContext(),
                        "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }


    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        sharestring = Medicinebundle.getMedicineName();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT,
                sharestring + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

}
