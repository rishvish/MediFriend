package com.example.rishabhvishwakarma.medifriend.reminders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rishabhvishwakarma.medifriend.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {

    Medicine Newrecord=new Medicine();

    public static ArrayList<Medicine> Medicines_list=new ArrayList<>();
    AutoCompleteTextView NameMedicine;
    EditText Dosage;
    public static String medicine_name,dosage;
    TextView Starttime;
    public TextView Starttimeedit;

    boolean medicine_type;
    RadioGroup RG,RGFreq;
    RadioButton oral;
    int Frequencynumber=-1;
    boolean checkedFreq;

    TextView fromDate,DocDate;
    Calendar newCalendar = Calendar.getInstance();
    Calendar startdatecalendar = Calendar.getInstance();
    Calendar Doctorapp = Calendar.getInstance();
    int Year,Month,Day,DocYear,DocMonth,DocDay;
    public static int DayHours=0;
    public static int HMins=0;

    public DatePickerDialog fromDatePickerDialog;
    SimpleDateFormat dateFormatter;
    static AlarmManager alarmManager;
    int alarmId;

    boolean checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        Starttime=findViewById(R.id.Start_Time);
        newCalendar.set(Calendar.HOUR_OF_DAY,DayHours);
        newCalendar.set(Calendar.MINUTE,HMins);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDate=(TextView) findViewById(R.id.date_start);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDateTimeField(0);
                fromDatePickerDialog.show();

            }


        });
        DocDate=(TextView) findViewById(R.id.Doc_app_date);
        DocDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDateTimeField(1);
                fromDatePickerDialog.show();
            }


        });

        Starttimeedit=(TextView) findViewById(R.id.timeStart);
        Starttimeedit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                timepickershow();
            }
        });
        NameMedicine=findViewById(R.id.Name_medicine);
        String[] medicines = getResources().getStringArray(R.array.medicines);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,medicines);
        NameMedicine.setAdapter(adapter);
        NameMedicine.setThreshold(1);
        Dosage=(EditText) findViewById(R.id.dosage_text);

        RG=(RadioGroup) findViewById(R.id.radiogp);
        RGFreq=(RadioGroup) findViewById(R.id.radiogpfreq);

        oral=(RadioButton) findViewById(R.id.Oral_drug_button);


        Button SaveRecord =  (Button) findViewById(R.id.savebutton);
        SaveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Newrecord.setMedicineName(NameMedicine.getText().toString());
                    medicine_name=NameMedicine.getText().toString();
                    dosage=((EditText) findViewById(R.id.dosage_text)).getText().toString();
                    Newrecord.setId(NameMedicine.getText().toString());

                    Newrecord.Date.set(Calendar.YEAR,Year);
                    Newrecord.Date.set(Calendar.MONTH,Month);
                    Newrecord.Date.set(Calendar.DAY_OF_MONTH,Day);
                    Newrecord.Date.set(Calendar.HOUR_OF_DAY,DayHours);

                    Newrecord.Date.set(Calendar.MINUTE,HMins);
                    int mm=Newrecord.Date.get(Calendar.MONTH);
                    int d=Newrecord.Date.get(Calendar.DAY_OF_MONTH);
                    int h= Newrecord.Date.get(Calendar.HOUR_OF_DAY);
                    int m=Newrecord.Date.get(Calendar.MINUTE);
                    mm=Newrecord.Date.get(Calendar.MONTH);

                    Newrecord.setDosage(Integer.parseInt(Dosage.getText().toString()));
                    Newrecord.setFrequency(Frequencynumber);
                    Newrecord.MedicineType = medicine_type;
                    Newrecord.DoctorApp.set(Calendar.YEAR,DocYear);
                    Newrecord.DoctorApp.set(Calendar.MONTH,DocMonth);
                    Newrecord.DoctorApp.set(Calendar.DAY_OF_MONTH,DocDay);
                    ((EditText) NameMedicine).setText("");
                    RG.clearCheck();
                    ((TextView) Starttimeedit).setText("");
                    ((TextView) fromDate).setText("");
                    ((EditText) Dosage).setText("");
                    ((TextView) DocDate).setText("");
                    RGFreq.clearCheck();


                    final Calendar NewDate1 = Calendar.getInstance();
                    NewDate1.set(Calendar.YEAR, Year);
                    NewDate1.set(Calendar.MONTH, Month);
                    NewDate1.set(Calendar.DAY_OF_MONTH, Day);
                    NewDate1.set(Calendar.HOUR_OF_DAY, DayHours);
                    NewDate1.set(Calendar.MINUTE, HMins);
                    Newrecord.setAlarms(NewDate1);
                    alarmId=convert(Newrecord.getMedicineName());
                    Log.d("AlarmDate"," "+NewDate1);
                    setalarmclock(NewDate1,alarmId);
                    if((Frequencynumber!=1)) {

                        for (int k = 0; k < Newrecord.getDosage()-1; k++) {

                            final Calendar NewDate = Calendar.getInstance();
                            NewDate.set(Calendar.YEAR, Year);
                            NewDate.set(Calendar.MONTH, Month);
                            NewDate.set(Calendar.DAY_OF_MONTH, Day);
                            NewDate.set(Calendar.HOUR_OF_DAY, DayHours);
                            NewDate.set(Calendar.MINUTE, HMins);


                            NewDate.add(Calendar.HOUR_OF_DAY, Frequencynumber);
                            Newrecord.setAlarms(NewDate);
                            Month = NewDate.get(Calendar.MONTH);
                            Day = NewDate.get(Calendar.DAY_OF_MONTH);
                            DayHours = NewDate.get(Calendar.HOUR_OF_DAY);
                            HMins = NewDate.get(Calendar.MINUTE);
                            Year = NewDate.get(Calendar.YEAR);

                            Calendar calsetdata=Calendar.getInstance();
                            calsetdata.set(Calendar.YEAR,Year);
                            calsetdata.set(Calendar.MONTH,Month);
                            calsetdata.set(Calendar.DAY_OF_MONTH,Day);
                            calsetdata.set(Calendar.HOUR_OF_DAY,DayHours);
                            calsetdata.set(Calendar.MINUTE,HMins);
                            alarmId=convert(Newrecord.getMedicineName())+k+1;
                            setalarmclock(NewDate,alarmId);

                        }

                    }
                    Medicines_list.add(Newrecord);
                    Newrecord=new Medicine();
                    Toast.makeText(getApplicationContext(),
                            "Medicine Saved.", Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Wrong Data", Toast.LENGTH_LONG).show();
                }
            }


        });
        Button ClearButton =  (Button) findViewById(R.id.clearbutton);
        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((EditText) NameMedicine).setText("");
                ((TextView) Starttimeedit).setText("");
                ((TextView) fromDate).setText("");
                ((EditText) Dosage).setText("");
                ((EditText) DocDate).setText("");
                if(checked==true)
                { RG.clearCheck();}
                if(checkedFreq==true)
                {
                    RGFreq.clearCheck();}
            }

        });


    }

    private void setDateTimeField(final int value) {

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if(value==0){
                    fromDate.setText(dateFormatter.format(newDate.getTime()));
                    Day = dayOfMonth;
                    Month = monthOfYear;
                    Year = year;}
                else if(value==1)
                {DocDate.setText(dateFormatter.format(newDate.getTime()));
                    DocDay = dayOfMonth;
                    DocMonth = monthOfYear;
                    DocYear = year;}

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }
    public void timepickershow(){// Process to get Current Time
        final Calendar c = Calendar.getInstance();
        DayHours = c.get(Calendar.HOUR_OF_DAY);
        HMins = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        Starttimeedit.setText(hourOfDay + ":" + minute);
                        DayHours=hourOfDay;
                        HMins=minute;
                    }
                }, DayHours, HMins, false);
        tpd.show();}


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Oral_drug_button:
                if (checked)
                    medicine_type=true;
                break;
            case R.id.injection_button:
                if (checked)
                    medicine_type=false;
                break;
        }
    }
    public void onRadioButtonFreqClicked(View v)
    {
        // Is the button now checked?
        checkedFreq = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.button_once:
                if (checkedFreq)
                    Frequencynumber=1;
                break;
            case R.id.button_4H:
                if (checkedFreq)
                    Frequencynumber=4;
                break;
            case R.id.button_6H:
                if (checkedFreq)
                    Frequencynumber=6;
                break;
            case R.id.button_8H:
                if (checkedFreq)
                    Frequencynumber=8;
                break;
            case R.id.button_12H:
                if (checkedFreq)
                    Frequencynumber=12;
                break;
            case R.id.button_24H:
                if (checkedFreq)
                    Frequencynumber=24;
                break;
        }
    }

    public  void setalarmclock(Calendar calparam,int id)
    {
        Log.d("MyActivity", "Alarm On");

        Intent myIntent = new Intent(AddReminder.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddReminder.this, id,myIntent, 0);
        Log.d("AlarmDate",calparam.toString());
        alarmManager.set(AlarmManager.RTC_WAKEUP, calparam.getTimeInMillis(),pendingIntent);


    }
    public int convert(String str) {
        int result=0;
        for (int i = 0; i < str.length(); i++) {
            result+= str.charAt(i);
        }

        return result;
    }

}
