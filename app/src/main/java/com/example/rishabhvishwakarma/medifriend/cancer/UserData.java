package com.example.rishabhvishwakarma.medifriend.cancer;

public class UserData {
    private String text;
    private String bitmap;

    public UserData(String text, String bitmap) {
        this.text = text;
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

}
