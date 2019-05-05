package com.example.rishabhvishwakarma.medifriend.reminders;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rishabhvishwakarma.medifriend.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllMedicinesTime_fragment extends Fragment {
    public ArrayList<Medicine> MedicineAdapter=new ArrayList<Medicine>();

    ArrayList<Medicine> MedicineList = new ArrayList<Medicine>();

    ArrayList<String> dataArrayList;
    ArrayList<String> dataArrayListtime;

    public AllMedicinesTime_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataArrayList = new ArrayList<String>();
        dataArrayListtime=new ArrayList<String>();
        try {
            MedicineList = getAllMedicines();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Calendar todaydate=Calendar.getInstance();
        int day=todaydate.get(Calendar.DAY_OF_MONTH);
        int month=todaydate.get(Calendar.MONTH);
        int year=todaydate.get(Calendar.YEAR);
        for (int i = 0; i <MedicineList.size(); i++) {
            for(int k=0;k<MedicineList.get(i).Alarms.size();k++){
                Medicine AdapterMitem=new Medicine();
                int Mday=MedicineList.get(i).getAlarms(k).get(Calendar.DAY_OF_MONTH);
                int Mmonth=MedicineList.get(i).getAlarms(k).get(Calendar.MONTH);
                int Myear=MedicineList.get(i).getAlarms(k).get(Calendar.YEAR);
                int h=MedicineList.get(i).getAlarms(k).get(Calendar.HOUR_OF_DAY);
                int m=MedicineList.get(i).getAlarms(k).get(Calendar.MINUTE);
                AdapterMitem.setDate(MedicineList.get(i).getAlarms(k));
                int l=AdapterMitem.getDate().get(Calendar.HOUR_OF_DAY);
                int b=AdapterMitem.getDate().get(Calendar.MINUTE);
                AdapterMitem.setMedicineName(" "+MedicineList.get(i).getMedicineName());
                if ((Mday==day)&&(Mmonth==month)&&(Myear==year)) {
                    MedicineAdapter.add(AdapterMitem);
                }
            }
        }
        Collections.sort(MedicineAdapter, new Comparator<Medicine>(){
            @Override
            public int compare(Medicine calendar, Medicine t1) {
                return calendar.Date.compareTo(t1.Date);
            }

        });

        int Hour=todaydate.get(Calendar.HOUR_OF_DAY);
        int Minute=todaydate.get(Calendar.MINUTE);
        for(int j=0;j< MedicineAdapter.size();j++)
        {
            int h=MedicineAdapter.get(j).Date.get(Calendar.HOUR_OF_DAY);
            int m=MedicineAdapter.get(j).Date.get(Calendar.MINUTE)+3;
            if(Hour>h)
            {
                MedicineAdapter.remove(j);
                j-=1;
            }
            else if(Hour==h)
            {
                if(Minute>m)
                {  MedicineAdapter.remove(j);
                    j-=1;}
           }
        }
        MyAdapter adapter = new MyAdapter(this.getContext(), MedicineAdapter);
        View rootView = inflater.inflate(R.layout.all_medicines_times, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.MTlistView);
        listView.setAdapter(adapter);
        return rootView;
    }
    private ArrayList<Medicine> getAllMedicines ()throws JSONException {
        SharedPreferences prefs = getActivity().getSharedPreferences("Fav_data", 0);
        String favs_json = prefs.getString("favs", null);
        if (favs_json != null) {
            Type t = new TypeToken<List<Medicine>>() {
            }.getType();
            return new Gson().fromJson(favs_json, t);
        }
        return new ArrayList();
    }
}
