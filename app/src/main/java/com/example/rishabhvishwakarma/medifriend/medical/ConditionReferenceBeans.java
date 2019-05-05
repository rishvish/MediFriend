package com.example.rishabhvishwakarma.medifriend.medical;

import android.os.Parcel;
import android.os.Parcelable;

class ConditionReferenceBeans implements Parcelable {

    private String bodyPart = null;
    private String symptom = null;
    private String warning = null;
    private String conditions = null;

    ConditionReferenceBeans() {
//        Required empty constructor
    }

    private ConditionReferenceBeans(Parcel parcel) {
        bodyPart = parcel.readString();
        symptom = parcel.readString();
        warning = parcel.readString();
        conditions = parcel.readString();
    }

    public static final Creator<ConditionReferenceBeans> CREATOR = new Creator<ConditionReferenceBeans>() {
        @Override
        public ConditionReferenceBeans createFromParcel(Parcel parcel) {
            return new ConditionReferenceBeans(parcel);
        }

        @Override
        public ConditionReferenceBeans[] newArray(int size) {
            return new ConditionReferenceBeans[size];
        }
    };

    void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    void setWarning(String warning) {
        this.warning = warning;
    }

    void setConditions(String conditions) {
        this.conditions = conditions;
    }


    String getConditions() {
        return conditions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bodyPart);
        parcel.writeString(symptom);
        parcel.writeString(warning);
        parcel.writeString(conditions);
    }
}
