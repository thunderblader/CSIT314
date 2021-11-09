package com.example.csit314.ui.login;

import android.util.Patterns;
import android.widget.EditText;

import com.example.csit314.R;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class theLoginActivityTest {

    @Test
    public void TestLogin() {
        String user_Email = "user@gmail.com";
        String user_Password = "password";

        EditText the_Email = null;
        EditText the_Password = null;

        if(user_Email.isEmpty() || user_Password.isEmpty())
        {
            the_Email.setError("Please enter a valid email and password");
            the_Email.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(user_Email).matches())
        {
            the_Email.setError("Please enter a valid email");
            the_Email.requestFocus();
            return;
        }
        else if(user_Password.length() < 6)
        {
            the_Password.setError("Please enter > 6 characters");
            the_Password.requestFocus();
            return;
        }
    }
}