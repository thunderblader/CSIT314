package com.example.csit314.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.useradminview.UserAdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class theLoginActivityTest {
    FirebaseAuth mAuth;
    Activity activity;

    @Test
    public void TestLoginLogoutAdmin() {
        //email and password
        String user_Email = "jerald@gmail.com";
        String user_Password = "password";

        //get firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //user input
        EditText the_Email = null;
        EditText the_Password = null;

        //conditions for input
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

        //sign in to database using email, password
        mAuth.signInWithEmailAndPassword(user_Email, user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(activity.getApplicationContext(), "login successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(activity.getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //sign out
        mAuth.signOut();
    }

    @Test
    public void TestLoginLogoutPharmacist() {
        //email and password
        String user_Email = "peter@gmail.com";
        String user_Password = "password";

        //get firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //user input
        EditText the_Email = null;
        EditText the_Password = null;

        //conditions for input
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

        //sign in to database using email, password
        mAuth.signInWithEmailAndPassword(user_Email, user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(activity.getApplicationContext(), "login successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(activity.getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //sign out
        mAuth.signOut();
    }

    @Test
    public void TestLoginLogoutDoctor() {
        //email and password
        String user_Email = "danny@gmail.com";
        String user_Password = "password";

        //get firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //user input
        EditText the_Email = null;
        EditText the_Password = null;

        //conditions for input
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

        //sign in to database using email, password
        mAuth.signInWithEmailAndPassword(user_Email, user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(activity.getApplicationContext(), "login successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(activity.getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //sign out
        mAuth.signOut();
    }

    @Test
    public void TestLoginLogoutPatient() {
        //email and password
        String user_Email = "johnson@gmail.com";
        String user_Password = "password";

        //get firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //user input
        EditText the_Email = null;
        EditText the_Password = null;

        //conditions for input
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

        //sign in to database using email, password
        mAuth.signInWithEmailAndPassword(user_Email, user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(activity.getApplicationContext(), "login successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(activity.getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //sign out
        mAuth.signOut();
    }
}