package com.example.csit314.prescribe;

import android.os.Parcel;
import android.os.Parcelable;

public class Prescription implements Parcelable {
    private String pName;
    private String pID;
    private String pDate;
    private String pAmount;
    private String pStatus;
    public Prescription()
    {
        pID = "ID";
        pName="name";
        pDate="date";
        pAmount="amount";
        pStatus ="status";

    }
    public Prescription(String prescriptionID,String prescriptionName, String prescriptionDate, String prescriptionAmt, String prescriptionStatus)
    {
        this.pID=prescriptionID;
        this.pName=prescriptionName;
        this.pDate=prescriptionDate;
        this.pAmount=prescriptionAmt;
        this.pStatus=prescriptionStatus;
    }

    protected Prescription(Parcel in) {
        pID = in.readString();
        pName = in.readString();
        pDate = in.readString();
        pAmount = in.readString();
        pStatus = in.readString();
    }

    public static final Creator<Prescription> CREATOR = new Creator<Prescription>() {
        @Override
        public Prescription createFromParcel(Parcel in) {
            return new Prescription(in);
        }

        @Override
        public Prescription[] newArray(int size) {
            return new Prescription[size];
        }
    };

    public String getpAmount() {
        return pAmount;
    }

    public String getpID() {
        return pID;
    }

    public boolean equals(Prescription o) {
        if (o instanceof Prescription) {
            Prescription c = o;
            return pName.equals(c.pName);
        }
        else return false;
    }

    public String getpDate() {
        return pDate;
    }
    @Override
    public String toString()
    {
        String s = getpName() + " " + getpStatus() + " " + getpStatus() + " " + getpDate();

        return s;
    }
    public String getpName() {
        return pName;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpAmount(String pAmount) {
        this.pAmount = pAmount;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pID);
        parcel.writeString(pName);
        parcel.writeString(pDate);
        parcel.writeString(pAmount);
        parcel.writeString(pStatus);
    }
}
