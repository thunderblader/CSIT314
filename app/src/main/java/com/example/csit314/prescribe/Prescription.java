package com.example.csit314.prescribe;

import android.os.Parcel;
import android.os.Parcelable;

public class Prescription implements Parcelable {
    private String pName;
    private String pDate;
    private String pAmount;
    private String pStatus;
    //private boolean isNew = true;
    public Prescription()
    {
        pName="name";
        pDate="date";
        pAmount="amount";
        pStatus ="status";

    }
    public Prescription(String prescriptionName, String prescriptionDate, String prescriptionAmt, String prescriptionStatus)
    {
        this.pName=prescriptionName;
        this.pDate=prescriptionDate;
        this.pAmount=prescriptionAmt;
        this.pStatus=prescriptionStatus;
        //isNew = (pStatus == "Completed");
    }

    protected Prescription(Parcel in) {
        pName = in.readString();
        pDate = in.readString();
        pAmount = in.readString();
        pStatus = in.readString();
       // isNew = in.readByte() != 0;
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

    public String getpDate() {
        return pDate;
    }

    public String getpName() {
        return pName;
    }

    public String getpStatus() {
        return pStatus;
    }

  //  public boolean isNew() {
    //    return isNew;
   // }

  // public void setNew(boolean aNew) {
   //     isNew = aNew;
   // }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pName);
        parcel.writeString(pDate);
        parcel.writeString(pAmount);
        parcel.writeString(pStatus);
    }
}
