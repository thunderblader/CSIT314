package com.example.csit314.useradminview;

import static org.junit.Assert.*;

import android.util.Patterns;
import android.widget.EditText;

import com.example.csit314.R;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserAdminAddActivityTest {

    @Test
    public void TestName() {
        String Name = "Jerald";
        EditText TextName = null;
        if(Name.isEmpty()){
            TextName.setError("Name is required");
            TextName.requestFocus();
            return;
        }
    }

    @Test
    public void TestEmail() {
        String Email = "Jerald@gmail.com";
        EditText TextEmail = null;
        if(Email.isEmpty()){
            TextEmail.setError("Email is required");
            TextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            TextEmail.setError("Please enter a valid email");
            TextEmail.requestFocus();
            return;
        }
    }

    @Test
    public void TestPassword() {
        String Password = "password";
        EditText TextPassword = null;
        if(Password.isEmpty()){
            TextPassword.setError("Password is required");
            TextPassword.requestFocus();
            return;
        }
        if(Password.length()<6){
            TextPassword.setError("Please enter > 6 characters");
            TextPassword.requestFocus();
            return;
        }
    }
    @Test
    public void TestNumber() {
        String Number = "+123";
        EditText TextNumber = null;
        if(Number.isEmpty()){
            TextNumber.setError("Number is required");
            TextNumber.requestFocus();
            return;
        }
    }
    @Test
    public void TestUser_Group() {
        String UserGroup = "admin";
        EditText TextUserGroup = null;
        if(UserGroup.isEmpty()){
            TextUserGroup.setError("User group is required");
            TextUserGroup.requestFocus();
            return;
        }
    }
}