package com.example.csit314.prescribe;

public class Prescription {
    private String pName;
    private String pDate;
    private String pAmount;
    private String pStatus;
    private boolean isNew = true;
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
        isNew = (pStatus == "Completed");
    }

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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
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
}
