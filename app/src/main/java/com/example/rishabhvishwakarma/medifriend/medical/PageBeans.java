package com.example.rishabhvishwakarma.medifriend.medical;

import android.os.Parcel;
import android.os.Parcelable;


class PageBeans implements Parcelable {

    private String field = "";
    private String topic = "";
    private String heading = "";
    private String content = "";

    PageBeans(){
        //Empty constructor
    }

    private PageBeans(Parcel parcel){
        field = parcel.readString();
        topic = parcel.readString();
        heading = parcel.readString();
        content = parcel.readString();
    }

    void setTopic(String topic){
        this.topic = topic;
    }

    void setHeading(String heading){
        this.heading = heading;
    }

    void setContent(String content){
        this.content = content;
    }
    String getHeading(){
        return heading;
    }

    String getContent(){
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(field);
        parcel.writeString(topic);
        parcel.writeString(heading);
        parcel.writeString(content);
    }

    public static final Creator<PageBeans> CREATOR = new Creator<PageBeans>() {
        @Override
        public PageBeans createFromParcel(Parcel parcel) {
            return new PageBeans(parcel);
        }

        @Override
        public PageBeans[] newArray(int size) {
            return new PageBeans[size];
        }
    };
}
