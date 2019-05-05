package com.example.rishabhvishwakarma.medifriend.medical;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class JSONReader {

    static String read(Context context, String fileName) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            context.getAssets().open(fileName)
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        String read;
        StringBuilder builder = new StringBuilder("");

        try {
            if (bufferedReader != null) {
                while ((read = bufferedReader.readLine()) != null) {
                    builder.append(read);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
