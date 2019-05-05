package com.example.rishabhvishwakarma.medifriend.medical;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rishabhvishwakarma.medifriend.R;

public class SymptomFragment extends Fragment {

    private String selectedSex = null;
    private Spinner selectBodyArea;
    private Spinner selectBodyPart;
    private Context context;
    private CheckedTextView[] allCheckedTextViews;
    private ActionBar actionBar;
    private Boolean isHomeAsUpShown;
    private String previousTitle, previousSubTitle;
    public OnSymptomFragmentInteractionListener mListener;

    public SymptomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_symptom, container, false);
        
        context = getContext();

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            isHomeAsUpShown = (actionBar.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            previousTitle = actionBar.getTitle().toString();
            previousSubTitle = actionBar.getSubtitle().toString();
            actionBar.setTitle(R.string.app_name);
            actionBar.setSubtitle(getString(R.string.check_subtitles));
        }

        selectBodyArea = rootView.findViewById(R.id.selected_body_area);
        selectBodyPart = rootView.findViewById(R.id.selected_body_part);
        CheckedTextView male = rootView.findViewById(R.id.radio_male);
        CheckedTextView female =  rootView.findViewById(R.id.radio_female);
        FloatingActionButton call108 =  rootView.findViewById(R.id.call_108);
        allCheckedTextViews = new CheckedTextView[] {male, female};

        selectBodyArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] bodyPartList;
                ArrayAdapter<String> bodyPartListAdapter;
                switch (position){
                    case 0:
                        if (selectBodyPart.getVisibility() == View.VISIBLE) {
                            selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.sink_up));
                            selectBodyPart.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        bodyPartList = getResources().getStringArray(R.array.head_area);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList
                        );
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        bodyPartList = getResources().getStringArray(R.array.chest_area);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList
                        );
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        bodyPartList = getResources().getStringArray(R.array.abdomen);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList
                        );
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        bodyPartList = getResources().getStringArray(R.array.back_area);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList
                        );
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        bodyPartList = getResources().getStringArray(R.array.pelvic);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList
                        );
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        bodyPartList = getResources().getStringArray(R.array.arm);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList
                        );
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        bodyPartList = getResources().getStringArray(R.array.legs);
                        bodyPartListAdapter = new ArrayAdapter<>(
                                context, R.layout.spinner_item, bodyPartList);
                        bodyPartListAdapter.setDropDownViewResource(R.layout.spinner_item);
                        bodyPartListAdapter.notifyDataSetChanged();
                        selectBodyPart.setAdapter(bodyPartListAdapter);
                        selectBodyPart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.float_down));
                        selectBodyPart.setVisibility(View.VISIBLE);
                        break;
                    default:
                        selectBodyPart.setSelection(0);
                        selectBodyPart.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        selectBodyPart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    startActivityIfFormComplete();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAction(view);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAction(view);
            }
        });

        call108.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CallEmergencyServices(getContext());
            }
        });

        return rootView;
    }

    private void startActivityIfFormComplete(){
        if (selectedSex != null && selectBodyArea.getSelectedItemPosition() > 0 && selectBodyPart.getSelectedItemPosition() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListener.onSymptomFragmentInteraction(
                            selectedSex,
                            selectBodyArea.getSelectedItem().toString(),
                            selectBodyPart.getSelectedItem().toString());
                }
            }, 200);
        }
        if (selectedSex == null){
            Toast.makeText(context, "Select gender to proceed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickAction(View v) {
        CheckedTextView temp = (CheckedTextView) v;
        if(temp !=null) {
            if (!temp.isChecked()) {
                for (CheckedTextView item : allCheckedTextViews) {
                    item.setChecked(false);
                    item.setTextColor(Color.parseColor("#000000"));
                }
                temp.setChecked(true);
                temp.setTextColor(Color.parseColor("#FFFFFF"));
                selectedSex = temp.getText().toString();
                temp.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_selected));
                startActivityIfFormComplete();
            } else {
                temp.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_deselected));
            }
        }
    }

    private void resetViews(){
        selectBodyArea.setSelection(0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSymptomFragmentInteractionListener) {
            mListener = (OnSymptomFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSymptomFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resetViews();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSymptomFragmentInteractionListener {
        void onSymptomFragmentInteraction(String selectedSex, String selectedBodyArea, String selectedBodyPart);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isHomeAsUpShown){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        actionBar.setTitle(previousTitle);
        actionBar.setSubtitle(previousSubTitle);
        super.onDestroy();
    }
}
