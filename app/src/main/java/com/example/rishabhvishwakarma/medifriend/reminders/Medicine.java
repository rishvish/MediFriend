package com.example.rishabhvishwakarma.medifriend.reminders;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Medicine implements Serializable {
    String MedicineName;
    boolean MedicineType; //true->oral drug
    Calendar Date=Calendar.getInstance();
    Calendar DoctorApp=Calendar.getInstance();;
    Integer Dosage;
    Integer frequency;
    ArrayList<Calendar> Alarms=new ArrayList<>();
    String Id;

    public Calendar getDoctorApp() {
        return DoctorApp;
    }

    public void setDoctorApp(Calendar doctorApp) {
        DoctorApp = doctorApp;
    }

    public Calendar getDate() {
        return Date;
    }

    public void setDate(Calendar date) {
        Date = date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Calendar getAlarms(int index) {
        return Alarms.get(index);
    }

    public void setAlarms(Calendar alarms) {
        Alarms.add(alarms);
        int x=alarms.get(Calendar.HOUR_OF_DAY);
        int z=alarms.get(Calendar.MINUTE);
        Log.d("Rishabh", "setting alarm");
    }

    public boolean isMedicineType() {
        return MedicineType;
    }

    public void setMedicineType(boolean medicineType) {
        MedicineType = medicineType;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public Integer getDosage() {
        return Dosage;
    }

    public void setDosage(Integer dosage) {
        Dosage = dosage;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

}
