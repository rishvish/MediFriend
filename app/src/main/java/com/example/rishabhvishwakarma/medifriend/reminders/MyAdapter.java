package com.example.rishabhvishwakarma.medifriend.reminders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rishabhvishwakarma.medifriend.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapter extends ArrayAdapter<Medicine> {
    Context context;
    ArrayList<Medicine> itemsArrayList;

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;


    public MyAdapter(Context context, ArrayList<Medicine> itemsArrayList) {
        super(context, R.layout.listitem_medicinestime, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_medicinestime_current, parent, false);
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_medicinestime, parent, false);
                break;
            }
        }
        final Calendar c = Calendar.getInstance();
        int hday = itemsArrayList.get(position).getDate().get(Calendar.HOUR_OF_DAY);
        int hmin = itemsArrayList.get(position).getDate().get(Calendar.MINUTE);

        String time = hday + ":" + hmin;

        TextView labelView = (TextView) convertView.findViewById(R.id.list_item_medicine_name);
        TextView valueView = (TextView) convertView.findViewById(R.id.list_item_medicine_time);

        labelView.setText(itemsArrayList.get(position).getMedicineName());
        valueView.setText(time);


        return convertView;
    }
    public int getViewTypeCount()
    {
        return VIEW_TYPE_COUNT;
    }
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

}
