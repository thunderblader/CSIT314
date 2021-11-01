package com.example.csit314.useradminview;

public class UserAdminHelper {
    private String name, number, user_group;

    public UserAdminHelper() {
    }

    public UserAdminHelper(String name, String number, String user_group) {
        this.name = name;
        this.number = number;
        this.user_group = user_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }
}
