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
public class UserAdminSearchActivityTest {

    @Test
    public void TestSearch() {
        String SearchEmail = "jerald@gmail.com";
        EditText EmailText = null;
        if(SearchEmail.isEmpty()){
            EmailText.setError("UID is required");
            EmailText.requestFocus();
            return;
        }
    }

    @Test
    public void TestUpdate() {
        String name = "john";
        String number = "+123";
        String userGrp = "doctor";

        EditText nameText = null;
        EditText numberText = null;
        EditText userGrpText = null;

        if(name.isEmpty()){
            nameText.setError("Name is required");
            nameText.requestFocus();
            return;
        }
        if(number.isEmpty()){
            numberText.setError("Number is required");
            numberText.requestFocus();
            return;
        }
        if(userGrp.isEmpty()){
            userGrpText.setError("User group is required");
            userGrpText.requestFocus();
            return;
        }
    }
}