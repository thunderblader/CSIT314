package com.example.csit314.patientview;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.csit314.prescribe.Prescription;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Parcelable, Serializable {
    ArrayList<Prescription> pAlist;
    String name,number ,email;
    public Patient(){

    }
    public Patient(String name,String number,String email,ArrayList<Prescription> pAlist) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.pAlist = new ArrayList<>();
        for (Prescription p : pAlist) {
            this.pAlist.add(p);
        }
    }

    protected Patient(Parcel in) {
        pAlist = in.createTypedArrayList(Prescription.CREATOR);
        name = in.readString();
        number = in.readString();
        email = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public ArrayList<Prescription> getAlist() {
        return pAlist;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pAlist);
        parcel.writeString(name);
        parcel.writeString(number);
        parcel.writeString(email);
    }
}
