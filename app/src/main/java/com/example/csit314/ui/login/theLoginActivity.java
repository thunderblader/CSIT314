package  com.example.csit314.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.patientview.Patient;
import com.example.csit314.pharmacyview.PharmacyViewPrescriptionActivity;
import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.PrescriptionActivity;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.useradminview.UserAdminActivity;

import java.util.ArrayList;
import java.util.Map;

public class theLoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding theLogin;
    Firebase the_firebase = new Firebase(this);

    private String user_Email;
    private String user_Password;

    private CountDownTimer login_timer;

    public Firebase get_firebase() { return the_firebase; }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        theLogin = ActivityLoginBinding.inflate(getLayoutInflater());

        final EditText the_Email = findViewById(R.id.username);
        final EditText the_Password = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.Login);

        the_firebase.signout();

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
                    the_firebase.signIn("theemail1234567@gmail.com", "123456");
                    login_now();
                }
            }
        });
    }
    public void login_now()
    {
        login_timer = new CountDownTimer(100,100)
        {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish()
            {
                if(the_firebase.is_database_ready())
                {
                    if(the_firebase.getThe_userType().equals("admin"))
                        launchUserAdminActivity();
                    else if(the_firebase.getThe_userType().equals("doctor"))
                    {
                        //launch doctor here
                    }
                    else if(the_firebase.getThe_userType().equals("patient"))
                        launchPatientActivity();
                    else
                        launchPharmacyActivity();
                }

            }
        };
        login_timer.start();
    }

    public void launchUserAdminActivity() //launch to UserAdminActivity
    {
        Intent i = new Intent(this, UserAdminActivity.class);
        startActivity(i);
    }
    public void launchPatientActivity() //launch to PatientActivity
    {
        Intent i = new Intent(this, PrescriptionActivity.class);
        Map p = the_firebase.get_pastprescription2("theemail1@gmail.com");
        ArrayList<Prescription> prescriptionAList = new ArrayList<>();
        if(p != null)
        {
            prescriptionAList = collectPrescription(p);
        }
        //i.putExtra("PrescriptionArrayList", prescriptionAList);
        i.putParcelableArrayListExtra("PrescriptionArrayList", prescriptionAList);
        startActivity(i);
    }
    public ArrayList<Prescription> collectPrescription(Map<String,Object> p)
    {
        ArrayList<Prescription> prescriptionAlist = new ArrayList<>();

        String id;
        String name;
        Long amount;
        String status;
        String date;
        if(p != null)
        {
            for (Map.Entry<String, Object> entry : p.entrySet()) {

                Map singlePrescription = (Map) entry.getValue();
                id = (String) entry.getKey();
                name = (String) singlePrescription.get("name");
                status = (String) singlePrescription.get("status");
                date = (String) singlePrescription.get("date");
                if (singlePrescription.get("amount") instanceof String)
                    amount = Long.parseLong((String) "0");
                else
                    amount = (Long) singlePrescription.get("amount");
                prescriptionAlist.add(new Prescription(id,name, date, String.valueOf(amount), status));
            }
        }
        return prescriptionAlist;
    }
    public void launchPharmacyActivity() //launch to PatientActivity
    {
        Intent i = new Intent(this, PharmacyViewPrescriptionActivity.class);
        ArrayList<Patient> patientAlist;
        Map p = the_firebase.searchUserGroup();
        patientAlist = collectUserAndPrescription(p);
        // i.putExtra("PatientArrayList",patientAlist);
        i.putParcelableArrayListExtra("PatientArrayList", patientAlist);
        i.putExtra("email",user_Email);
        i.putExtra("password",user_Password);
        startActivity(i);
    }
    public ArrayList<Patient> collectUserAndPrescription(Map<String,Object> patient)
    {
        ArrayList<Patient> patientAlist = new ArrayList<>();


        String name;
        String email;
        String number;
        for (Map.Entry<String, Object> entry: patient.entrySet())
        {
            Map singleUser = (Map) entry.getValue();
            name = (String) singleUser.get("name");
            email = entry.getKey().replace("_",".");
            number = (String) singleUser.get("number");
            patientAlist.add(new Patient(name,number,email,collectPrescription(the_firebase.get_pastprescription2(email))));
        }
        return patientAlist;
    }
}