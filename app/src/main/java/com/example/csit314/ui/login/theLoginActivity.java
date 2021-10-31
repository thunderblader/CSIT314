package  com.example.csit314.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class theLoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding theLogin;
    Firebase firebase = new Firebase(this);

    private EditText the_Email = theLogin.username;
    private EditText the_Password = theLogin.password;

    private Button loginButton = theLogin.login;
    private Button addButton;
    private Button backButton;

    private String user_Email;
    private String user_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        theLogin = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(theLogin.getRoot());

        the_Email = findViewById(R.id.AddEmailText);
        the_Password = findViewById(R.id.AddPasswordText);

        addButton = findViewById(R.id.AddAddButton);
        backButton = findViewById(R.id.AddBackButton);

        addButton.setOnClickListener(new View.OnClickListener()
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
                else if(user_Password.length()<6)
                {
                    the_Password.setError("Please enter > 6 characters");
                    the_Password.requestFocus();
                    return;
                }
            }
        });

        backButton.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        }));
    }
}