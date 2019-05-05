package com.example.rishabhvishwakarma.medifriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.rishabhvishwakarma.medifriend.medical.CallEmergencyServices;
import com.example.rishabhvishwakarma.medifriend.medical.Introduction;

public class SplashScreenActivity extends AppCompatActivity {

    private boolean startedBefore = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        createShortcuts();

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    startActivity(new Intent(SplashScreenActivity.this, Introduction.class));
                    startedBefore = false;

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();

                    // Finish This Activity
                    SplashScreenActivity.this.finish();
                }

                //  If the activity has been started before...
                else {
                    startedBefore = true;
                }
            }
        });

        // Start the thread
        t.start();

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (startedBefore) {
                    final Handler handler = new Handler();

                    final TextView text = findViewById(R.id.textsplash);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text.append("L");
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    text.append("o");
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            text.append("a");
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    text.append("d");
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            text.append("i");
                                                            handler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    text.append("n");
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            text.append("g");
                                                                            handler.postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    text.append(".");
                                                                                    handler.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            text.append(".");
                                                                                            handler.postDelayed(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {
                                                                                                    text.append(".");
                                                                                                    handler.postDelayed(new Runnable() {
                                                                                                        @Override
                                                                                                        public void run() {
                                                                                                            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));

                                                                                                            SplashScreenActivity.this.finish();

                                                                                                        }
                                                                                                    }, 1500);
                                                                                                }
                                                                                            }, 300);
                                                                                        }
                                                                                    }, 300);
                                                                                }
                                                                            }, 300);
                                                                        }
                                                                    }, 300);
                                                                }
                                                            }, 300);
                                                        }
                                                    }, 300);
                                                }
                                            }, 300);
                                        }
                                    }, 300);
                                }
                            }, 300);
                        }
                    }, 300);
                }
            }
        }, 0);
    }

    private void createShortcuts() {
        ShortcutManager shortcutManager;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            shortcutManager = getSystemService(ShortcutManager.class);

            Intent call108 = new Intent(this, CallEmergencyServices.class);
            call108.setAction(Intent.ACTION_VIEW);

            ShortcutInfo call108Shortcut = new ShortcutInfo.Builder(this, "call_108")
                    .setIntent(call108)
                    .setShortLabel(getString(R.string.emergency_number))
                    .setLongLabel(getString(R.string.call_emergency_services))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_app_logo_png))
                    .setRank(5)
                    .build();


        }
    }
}
