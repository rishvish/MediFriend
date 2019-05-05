package com.example.rishabhvishwakarma.medifriend.reports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhvishwakarma.medifriend.R;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

        private Context context;

        public FingerprintHandler(Context context) {
                this.context = context;
        }

        public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
                CancellationSignal cancellationSignal = new CancellationSignal();
                fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
                this.update("There was an Auth Error. " + errString, false);
        }

        @Override
        public void onAuthenticationFailed() {
                this.update("Auth Failed. ", false);
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                this.update("Error: " + helpString, false);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                this.update("You can now access the feature.", true);
                Intent intent=new Intent(context,ReportsActivity.class);
                intent.putExtra("email", ReportsAuthActivity.email);
                intent.putExtra("UID", ReportsAuthActivity.uid);
                context.startActivity(intent);
        }

        private void update(String s, boolean b) {
                TextView paraLabel =  ((Activity) context).findViewById(R.id.paraLabel);
                ImageView imageView = ((Activity) context).findViewById(R.id.fingerprintImage);
                paraLabel.setText(s);
                if (b == false) {
                        paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                } else {
                        paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        imageView.setImageResource(R.mipmap.action_done);
                }
        }
}

