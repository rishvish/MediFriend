package com.example.rishabhvishwakarma.medifriend.medical;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.rishabhvishwakarma.medifriend.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private final SparseBooleanArray mCheckedItems = new SparseBooleanArray();
    private final List<String> mValues;
    private final Context context;
    private final boolean isViewChecked;
    private int lastPosition = -1;

    RecyclerViewAdapter(boolean isViewChecked, Context context, List<String> items) {
        this.isViewChecked = isViewChecked;
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (isViewChecked){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item_check, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String valueForPosition = mValues.get(position);
        if (isViewChecked) {
            holder.mIdCheckedTextView.setText(valueForPosition);
            holder.mIdCheckedTextView.setChecked(mCheckedItems.get(position));
            holder.mIdCheckedTextView.setSelected(mCheckedItems.get(position));
            if (holder.mIdCheckedTextView.isChecked()) {
                holder.mIdCheckedTextView.setTextColor(Color.parseColor("#FFFFFF"));
            } else holder.mIdCheckedTextView.setTextColor(Color.parseColor("#000000"));

            setAnimation(holder.mIdCheckedTextView, position);
        } else {
            holder.mIdTextView.setText(valueForPosition);
            setAnimation(holder.mIdTextView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return String.valueOf(mValues.get(position).trim().charAt(0));
    }

    private void setAnimation(View viewToAnimate, int position){
        if (position > lastPosition){
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
            lastPosition = position;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mIdTextView;
        CheckedTextView mIdCheckedTextView;

        ViewHolder(View view) {
            super(view);
            mIdTextView = null;
            mIdCheckedTextView = null;
            if (isViewChecked) {
                mIdCheckedTextView = (CheckedTextView) view.findViewById(R.id.list_item_checked_textview);
                mIdCheckedTextView.setOnClickListener(this);
            } else {
                mIdTextView = (TextView) view.findViewById(R.id.recycler_view_list_item);
                mIdTextView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_item_checked_textview:
                    int position = this.getAdapterPosition();
                    mCheckedItems.put(position, !mIdCheckedTextView.isChecked());
                    ((SymptomCheck)context).onRecyclerViewItemClick(mIdCheckedTextView, position + 1, getItemCount());
                    break;
                default:
                    break;
            }
        }
        @Override
        public String toString() {
            if (isViewChecked){
                return super.toString() + " '" + mIdCheckedTextView.getText() + "'";
            } else {
                return super.toString() + " '" + mIdTextView.getText() + "'";
            }
        }
    }
}
