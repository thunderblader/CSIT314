package  com.example.csit314.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.useradminview.UserAdminActivity;

public class theLoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding theLogin;
    Firebase the_firebase = new Firebase(this);

    private String user_Email;
    private String user_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        theLogin = ActivityLoginBinding.inflate(getLayoutInflater());

        final EditText the_Email = findViewById(R.id.username);
        final EditText the_Password = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.Login);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_Email = the_Email.getText().toString();
                user_Password = the_Password.getText().toString();

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
                else
                {
                    String user_Type_admin = "admin";
                    //if(user_Type_admin == the_firebase.getThe_userType())
                        launchUserAdminActivity(v);
                }
            }
        });
    }

    public void launchUserAdminActivity(View v) //launch to UserAdminActivity
    {
        Intent i = new Intent(this, UserAdminActivity.class);
        startActivity(i);
    }
}