package com.example.rishabhvishwakarma.medifriend;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.rishabhvishwakarma.medifriend.cancer.MelanomaActivity;
import com.example.rishabhvishwakarma.medifriend.maps.MapsFragment;
import com.example.rishabhvishwakarma.medifriend.medical.CompleteConditionList;
import com.example.rishabhvishwakarma.medifriend.medical.FirstAidCheck;
import com.example.rishabhvishwakarma.medifriend.medical.FirstAidFragment;
import com.example.rishabhvishwakarma.medifriend.medical.PossibleConditionDetails;
import com.example.rishabhvishwakarma.medifriend.medical.SymptomCheck;
import com.example.rishabhvishwakarma.medifriend.medical.SymptomFragment;
import com.example.rishabhvishwakarma.medifriend.news.NewsFragment;
import com.example.rishabhvishwakarma.medifriend.reminders.ReminderActivity;

import com.example.rishabhvishwakarma.medifriend.reports.ReportsAuthActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.codetail.animation.SupportAnimator;

import static io.codetail.animation.ViewAnimationUtils.createCircularReveal;

public class Home extends AppCompatActivity implements
        SymptomFragment.OnSymptomFragmentInteractionListener,
        FirstAidFragment.OnFirstAidListFragmentInteractionListener, CompleteConditionList.OnCompleteConditionsInteractionListener {

    private FragmentManager fragmentManager;
    public static BottomNavigationView navigation;
    private ActionBar actionBar;
    private final int[] touchCoordinate = new int[2];
    private FrameLayout homeView;
    private CardView reminders,reports,cancer_scan,check_symptoms,check_conditions,first_aid;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressWarnings("ConstantConditions")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_by_home:
                    if (fragmentManager.findFragmentByTag("HomeFragment") == null) {
                        onBackPressed();
                        homeView.setVisibility(View.VISIBLE);
                    }
                    return true;
                case R.id.navigation_by_news:
                    if (fragmentManager.findFragmentByTag("newsFragment") == null) {
                        homeView.setVisibility(View.INVISIBLE);
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.float_up, R.anim.sink_up)
                                .replace(R.id.main_fragment_container, new NewsFragment(), "newsFragment")
                                .commit();
                    }
                    return true;
                case R.id.navigation_by_maps:
                    if (fragmentManager.findFragmentByTag("mapFragment") == null) {
                        homeView.setVisibility(View.INVISIBLE);
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.float_up, R.anim.sink_up)
                                .replace(R.id.main_fragment_container, new MapsFragment(), "mapsFragment")
                                .commit();
                    }
                    return true;
                default:
                    return false;
            }
        }
    };


    @Override
    protected void onResume() {
        if (navigation.getVisibility() == View.INVISIBLE) {
            navigation.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setSubtitle("Prevention is Better than Cure");
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeView =findViewById(R.id.home_fragment);
        check_conditions = findViewById(R.id.symptomsCard);
        reminders = findViewById(R.id.remindersCard);
        reports = findViewById(R.id.reportsCard);
        first_aid=findViewById(R.id.firstAidCard);
        check_symptoms = findViewById(R.id.conditionsCard);
        cancer_scan = findViewById(R.id.cancerScanCard);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(Home.this, LoginActivity.class));
                } else {
                    user = firebaseAuth.getCurrentUser();
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("894155687422-eb2k22lr65815oi30cd8aqln4uanvfji.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home.this, ReminderActivity.class));
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, ReportsAuthActivity.class);
                intent.putExtra("email", user.getEmail());
                intent.putExtra("UID", user.getUid());
                startActivity(intent);
            }
        });

        cancer_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MelanomaActivity.class);
                intent.putExtra("email", user.getEmail());
                intent.putExtra("UID", user.getUid());
                //Toast.makeText(Home1.this, user.getUid(), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        check_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeView.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.float_up, R.anim.sink_up)
                        .replace(R.id.main_fragment_container, new SymptomFragment(), "symptomFragment")
                        .commit();
            }
        });


        first_aid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeView.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.float_up, R.anim.sink_up)
                        .replace(R.id.main_fragment_container, new FirstAidFragment(), "firstAidFragment")
                        .commit();
                }
        });
        check_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeView.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.float_up, R.anim.sink_up)
                        .replace(R.id.main_fragment_container, new CompleteConditionList(), "AllConditionsFragment")
                        .commit();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        touchCoordinate[0] = (int) ev.getX();
        touchCoordinate[1] = (int) ev.getY();
        return super.dispatchTouchEvent(ev);
    }

    private void animationForward(View mRevealView, int[] center){
        int centerX = center[0];
        int centerY = center[1];
        int startRadius = 0;
        int endRadius = (int) (Math.hypot(mRevealView.getWidth() * 2, mRevealView.getHeight() * 2));
        SupportAnimator animator = createCircularReveal(mRevealView, centerX, centerY, startRadius, endRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(800);

        animator.start();
        mRevealView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_about:
                final FrameLayout revealView = this.findViewById(R.id.menu_fragment_container);
                revealView.setBackgroundResource(R.color.colorSecondary);
                animationForward(revealView, touchCoordinate);
                revealView.setVisibility(View.VISIBLE);
                navigation.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isFragmentActive("completeConditionList"))
                            fragmentManager.beginTransaction().replace(R.id.menu_fragment_container, new About(), "about").commit();
                        else fragmentManager.beginTransaction().add(R.id.menu_fragment_container, new About(), "about").commit();
                    }
                }, 600);
                return true;
            case R.id.action_contact:
                Toast.makeText(this, "Contacting the Developer", Toast.LENGTH_SHORT).show();
                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "rishabhvish98@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "MediFriend: Contact Us");
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(this, "Can't send a mail.", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_signOut:
                Toast.makeText(this, "Signing out...", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onSymptomFragmentInteraction(String selectedSex, String selectedBodyArea, String selectedBodyPart) {
        Intent intent = new Intent(this, SymptomCheck.class);
        intent.putExtra("selectedSex", selectedSex);
        intent.putExtra("selectedBodyArea", selectedBodyArea);
        intent.putExtra("selectedBodyPart", selectedBodyPart);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(boolean flag, final String item) {
        if (flag) {
//            FOR LAUNCHING THE FIRST AID DETAILS
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Home.this, FirstAidCheck.class).putExtra("topic", item));
                }
            }, 200);
        } else {
//            FOR SCROLLING OF RECYCLERVIEW
            if (item.equals("up")) {
                if (navigation.getVisibility() == View.VISIBLE) {
                    navigation.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.sink_down));
                    navigation.setVisibility(View.INVISIBLE);
                }
            }
            else if (item.equals("down")) {
                if (navigation.getVisibility() == View.INVISIBLE) {
                    navigation.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.float_up));
                    navigation.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onCompleteConditionsInteraction(final String item) {
        final View revealView = this.findViewById(R.id.fab_condition_details_revealView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.setBackgroundResource(R.color.colorSecondary);
            }
        }, 400);
        animationForward(revealView, touchCoordinate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.setBackgroundResource(R.color.colorDisabledLight);
                fragmentManager.beginTransaction()
                        .add(
                                R.id.fab_condition_details_revealView,
                                PossibleConditionDetails.newInstance(item),
                                "conditionDetails"
                        )
                        .commit();
            }
        }, 500);
    }


    private void removeFragment(String tag) {
        if (fragmentManager.findFragmentByTag(tag) != null) {
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(tag))
                    .commit();
        }
       FrameLayout revealView;
        switch (tag) {
            case "about":
                revealView = this.findViewById(R.id.menu_fragment_container);
                revealView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                revealView.setVisibility(View.INVISIBLE);
                navigation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.float_up));
                navigation.setVisibility(View.VISIBLE);
                break;
            case "completeConditionList":
                revealView = this.findViewById(R.id.menu_fragment_container);
                revealView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                revealView.setVisibility(View.INVISIBLE);
                actionBar.show();
                navigation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.float_up));
                navigation.setVisibility(View.VISIBLE);
                break;
            case "conditionDetails":
                revealView = this.findViewById(R.id.fab_condition_details_revealView);
                revealView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                revealView.setVisibility(View.INVISIBLE);
                break;
            case "firstAidFragment":
                revealView = this.findViewById(R.id.menu_fragment_container);
                revealView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                revealView.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
    }

    private boolean isFragmentActive(String tag) {
        return fragmentManager.findFragmentByTag(tag) != null && fragmentManager.findFragmentByTag(tag).isAdded();
    }

    @Override
    public void onBackPressed() {
        if (isFragmentActive("conditionDetails")) {
            removeFragment("conditionDetails");
            //homeView.setVisibility(View.VISIBLE);
        }
        else if (isFragmentActive("AllConditionsFragment")) {
            removeFragment("AllConditionsFragment");
            homeView.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.medifriend);
            actionBar.setSubtitle(R.string.home_subtitle);

        }
        else if (isFragmentActive("about")) {
            removeFragment("about");
            //homeView.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.medifriend);
            actionBar.setSubtitle(R.string.home_subtitle);
        }
        else if (isFragmentActive("symptomFragment") ){
            removeFragment("symptomFragment");
            homeView.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.medifriend);
            actionBar.setSubtitle(R.string.home_subtitle);

        }
        else if (isFragmentActive("firstAidFragment") ){
            removeFragment("firstAidFragment");
            homeView.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.medifriend);
            actionBar.setSubtitle(R.string.home_subtitle);
        }
        else if (isFragmentActive("newsFragment") ){
            removeFragment("newsFragment");
            homeView.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.medifriend);
            actionBar.setSubtitle(R.string.home_subtitle);
        }
        else if (isFragmentActive("mapsFragment") ){
            removeFragment("mapsFragment");
            homeView.setVisibility(View.VISIBLE);
            actionBar.setTitle(R.string.medifriend);
            actionBar.setSubtitle(R.string.home_subtitle);
        }
        else
            super.onBackPressed();
    }
}