package com.example.rishabhvishwakarma.medifriend;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

/*
* Created by Rishabh on 03/01/2019.
*/
public class About extends Fragment {

    private String previousTitle;
    private String previousSubTitle;
    private ActionBar actionBar;
    private ScrollView scrollView;
    private boolean isHomeAsUpShown = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);

        scrollView = rootView.findViewById(R.id.about_container);
        scrollView.startAnimation(
                AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in)
        );
        scrollView.setVisibility(View.VISIBLE);
//        SET ACTION BAR
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            isHomeAsUpShown = (actionBar.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            previousTitle = actionBar.getTitle().toString();
            previousSubTitle = actionBar.getSubtitle().toString();
            actionBar.setTitle(R.string.about);
            actionBar.setSubtitle(R.string.medifriend);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_about, menu);
    }

    @Override
    public void onDestroy() {
        if (!isHomeAsUpShown){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        actionBar.setTitle(previousTitle);
        actionBar.setSubtitle(previousSubTitle);
        scrollView.setVisibility(View.INVISIBLE);
        super.onDestroy();
    }
}
