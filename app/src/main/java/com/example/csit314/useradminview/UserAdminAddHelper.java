package com.example.csit314.useradminview;

public class UserAdminAddHelper {

    String txt_Name;
    String txt_Email;
    String txt_Password;
    String txt_Number;
    String txt_UserGroup;

    public UserAdminAddHelper(){
    }

    public UserAdminAddHelper(String txt_Name, String txt_Email, String txt_Password, String txt_Number, String txt_UserGroup) {
        this.txt_Name = txt_Name;
        this.txt_Email = txt_Email;
        this.txt_Password = txt_Password;
        this.txt_Number = txt_Number;
        this.txt_UserGroup = txt_UserGroup;
    }

    public String getTxt_Name() {
        return txt_Name;
    }

    public void setTxt_Name(String txt_Name) {
        this.txt_Name = txt_Name;
    }

    public String getTxt_Email() {
        return txt_Email;
    }

    public void setTxt_Email(String txt_Email) {
        this.txt_Email = txt_Email;
    }

    public String getTxt_Password() {
        return txt_Password;
    }

    public void setTxt_Password(String txt_Password) {
        this.txt_Password = txt_Password;
    }

    public String getTxt_Number() {
        return txt_Number;
    }

    public void setTxt_Number(String txt_Number) {
        this.txt_Number = txt_Number;
    }

    public String getTxt_UserGroup() {
        return txt_UserGroup;
    }

    public void setTxt_UserGroup(String txt_UserGroup) {
        this.txt_UserGroup = txt_UserGroup;
    }
}
