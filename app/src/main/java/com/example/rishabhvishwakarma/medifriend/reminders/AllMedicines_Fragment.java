package com.example.rishabhvishwakarma.medifriend.reminders;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rishabhvishwakarma.medifriend.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllMedicines_Fragment extends Fragment {

    private ArrayAdapter<String> MedicineAdapter;
    ArrayList<String> dataArrayList;
    MedicineHandler hand;
    public static final String FAV_PREF_NAME = "Fav_data";
    private static final String FAV_TAG = "favs";
    Medicine Mobject=new Medicine();
    ArrayList<Medicine> MedicineList = new ArrayList<Medicine>();
    Medicine Medicinedeleted;
    public AllMedicines_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        hand = new MedicineHandler() {
            @Override
            public void addToMedicineList(Medicine m) throws JSONException {
                int s=m.Date.get(Calendar.DAY_OF_MONTH);
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                String medicine_json = new Gson().toJson(m);
                String favs = prefs.getString(FAV_TAG, null);

                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    if (!hand.foundInMedicineList(ar, m)) {
                        ar.put(new JSONObject(medicine_json));
                        editor.putString(FAV_TAG, ar.toString());
                        editor.apply();
                    }
                } else {
                    JSONArray ar = new JSONArray();
                    ar.put(new JSONObject(medicine_json));
                    editor.putString(FAV_TAG, ar.toString());
                    editor.apply();
                }
            }

            @Override
            public void deleteFromMedicineList(Medicine m) throws JSONException {
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();

                String favs = prefs.getString(FAV_TAG, null);
                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    int index = hand.findIndex(ar, m);
                    if (index >= 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            ar.remove(index);
                            editor.putString(FAV_TAG, ar.toString());
                            editor.apply();
                        }
                    }
                }
            }

            @Override
            public ArrayList<Medicine> getChosenMedicine() throws JSONException {
                SharedPreferences prefs = getActivity().getSharedPreferences(FAV_PREF_NAME, 0);
                String favs = prefs.getString(FAV_TAG, null);
                if (favs != null) {
                    JSONArray ar = new JSONArray(favs);
                    Type t = new TypeToken<List<Medicine>>() {
                    }.getType();
                    return new Gson().fromJson(ar.toString(), t);
                } else {
                    return new ArrayList<Medicine>();
                }
            }

            @Override
            public int findIndex(JSONArray ar, Medicine m) throws JSONException {
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject obj = ar.getJSONObject(i);
                    Type t = new TypeToken<Medicine>() {
                    }.getType();
                    Medicine mov = new Gson().fromJson(obj.toString(), t);
                    if (mov.getId().equalsIgnoreCase(m.getId())) {
                        return i;
                    }
                }
                return -1;//-1
            }

            @Override
            public boolean foundInMedicineList(JSONArray ar, Medicine m) throws JSONException {
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject obj = ar.getJSONObject(i);
                    if (obj.getString("Id").equalsIgnoreCase(m.getId())) {
                        return true;
                    }
                }
                return false;
            }

        };

        Bundle x = new Bundle();
        x = this.getArguments();
        if (x == null) {
            x = getActivity().getIntent().getExtras();
        }
        if (x != null) {
            Medicinedeleted = (Medicine) x.getSerializable("Medicineobject_delete");
            for(int j=0;j<Medicinedeleted.Alarms.size();j++){
                int id =convert(Medicinedeleted.getMedicineName())+j;
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                        id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AddReminder.alarmManager.cancel(pendingIntent);
            }
            try {
                hand.deleteFromMedicineList(Medicinedeleted);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataArrayList = new ArrayList<String>();
        for(int i = 0; i<AddReminder.Medicines_list.size(); i++) {
            Mobject = AddReminder.Medicines_list.get(i);
            int z= Mobject.Date.get(Calendar.DAY_OF_MONTH);


            try {
                if(Mobject.Id!=null)
                {hand.addToMedicineList(Mobject);
                    AddReminder.Medicines_list=new ArrayList<Medicine>();}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            MedicineList = getAllMedicines();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < MedicineList.size(); i++) {

            String MName = MedicineList.get(i).MedicineName;
            dataArrayList.add(MName);

        }


        MedicineAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.listitem_medicines, // The name of the layout ID.
                        R.id.list_item_medicine_name, // The ID of the textview to populate.
                        dataArrayList);

        View rootView = inflater.inflate(R.layout.all_medicines, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(MedicineAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Medicine MObj=MedicineList.get(position);
                int m=MedicineList.get(position).Date.get(Calendar.DAY_OF_MONTH);
                //String Medicineitem = MedicineAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MedicineDetail.class);// medicine detail
                //.putExtra(Intent.EXTRA_TEXT, MObj);
                Bundle extras = new Bundle();
                extras.putSerializable("Medicineobject_details", MObj);
                intent.putExtra("Medicineobject_details", MObj);
                startActivity(intent);
            }
        });

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
    public int convert(String str) {
        int result=0;
        for (int i = 0; i < str.length(); i++) {
            result+= str.charAt(i);
        }

        return result;
    }

}
