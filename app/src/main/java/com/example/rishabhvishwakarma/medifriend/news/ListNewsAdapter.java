package com.example.rishabhvishwakarma.medifriend.news;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rishabhvishwakarma.medifriend.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

class ListNewsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListNewsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage =  convertView.findViewById(R.id.galleryImage);
            holder.author =  convertView.findViewById(R.id.author);
            holder.title =  convertView.findViewById(R.id.title);
            holder.sdetails = convertView.findViewById(R.id.sdetails);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            if(song.get(NewsFragment.KEY_AUTHOR)=="null"){
                holder.author.setVisibility(View.INVISIBLE);
            }else{
            holder.author.setText("-"+song.get(NewsFragment.KEY_AUTHOR));
            }
            holder.title.setText(song.get(NewsFragment.KEY_TITLE));
            holder.sdetails.setText(song.get(NewsFragment.KEY_DESCRIPTION));

            if(song.get(NewsFragment.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.get()
                        .load(song.get(NewsFragment.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

