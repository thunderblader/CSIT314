package com.example.csit314.useradminview;

public class UserAdminAddHelper {

    String Name;
    String Email;
    String Password;
    String Number;
    String UserGroup;

    public UserAdminAddHelper(){
    }

    public UserAdminAddHelper(String Name, String Email, String Password, String Number, String UserGroup) {
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Number = Number;
        this.UserGroup = UserGroup;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getUserGroup() {
        return UserGroup;
    }

    public void setUserGroup(String UserGroup) {
        this.UserGroup = UserGroup;
    }
}
