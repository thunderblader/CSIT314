package  com.example.csit314.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.PrescriptionActivity;
import com.example.csit314.useradminview.UserAdminActivity;
import com.example.csit314.useradminview.UserAdminHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private Firebase firebase_object = new Firebase(this);

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String temp;

            //firebase_object.signout();
            firebase_object.signIn("theemail1234567@gmail.com", "123456");
            //firebase_object.createAccount("theemail1@gmail.com", "123456", "111", "222","333");
            //temp = firebase_object.getThe_number();
            /*temp = firebase_object.getThe_userType();
            temp = firebase_object.getThe_name();
            firebase_object.signout();
            firebase_object.signIn("thunderblader@live.com", "123456");
            temp = firebase_object.getUID();*/
            //firebase_object.getDatabase();

     binding = ActivityLoginBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) //when you clock the login button
            {
                //this is hard coded for testing
                //below is what is supposed to happen after you login, but do not end the program or move it to another page until firebase has logged in
                //loadingProgressBar.setVisibility(View.VISIBLE);
                //loginViewModel.login(usernameEditText.getText().toString(),
                //        passwordEditText.getText().toString());
                //launchPatientActivity(v);
                launchUserAdminActivity(v);
            }
        });
    }

    public void launchPatientActivity(View v) //launch to PatientActivity
    {
        Intent i = new Intent(this, PrescriptionActivity.class);
        Map p = firebase_object.get_pastprescription2("theemail1234567@gmail.com");
        ArrayList<Prescription> prescriptionAList;
        prescriptionAList = collectPrescription(p);
        i.putExtra("ArrayList",prescriptionAList);
        startActivity(i);
    }
    public ArrayList<Prescription> collectPrescription(Map<String,Object> p)
    {
        ArrayList<Prescription> prescriptionAlist = new ArrayList<>();

        String name;
        Long amount;
        String status;
        String date;
            for (Map.Entry<String, Object> entry: p.entrySet())
            {
                Map singlePrescription = (Map) entry.getValue();
                name = (String) singlePrescription.get("name");
                status = (String) singlePrescription.get("status");
                date = (String) singlePrescription.get("date");
                if(singlePrescription.get("amount") instanceof String)
                    amount = Long.parseLong((String)"0");
                else
                    amount = (Long) singlePrescription.get("amount");
                prescriptionAlist.add(new Prescription(name, date, String.valueOf(amount), status));
            }
        return prescriptionAlist;
    }
    public void launchUserAdminActivity(View v) //launch to UserAdminActivity
    {
        Intent i = new Intent(this, UserAdminActivity.class);
        startActivity(i);
    }
    public ArrayList<UserAdminHelper> collectUser(Map<String,Object> p)
    {
        ArrayList<UserAdminHelper> collectUserAlist = new ArrayList<>();

        String name;
        String number;
        String user_group;
        for (Map.Entry<String, Object> entry: p.entrySet())
        {
            Map singleUser = (Map) entry.getValue();
            user_group = (String) singleUser.get("user_type");
            name = (String) singleUser.get("name");
            number = (String) singleUser.get("number");

            collectUserAlist.add(new UserAdminHelper(name, number, user_group));
        }
        return collectUserAlist;
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}