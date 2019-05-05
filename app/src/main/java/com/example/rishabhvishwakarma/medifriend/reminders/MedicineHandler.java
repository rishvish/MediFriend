package com.example.rishabhvishwakarma.medifriend.reminders;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

interface MedicineHandler {
    void addToMedicineList(Medicine m) throws Exception;
    void deleteFromMedicineList(Medicine m) throws Exception;
    ArrayList<Medicine> getChosenMedicine() throws JSONException;
    int findIndex(JSONArray ar, Medicine m) throws JSONException;
    boolean foundInMedicineList(JSONArray ar, Medicine m) throws JSONException;
}
