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
import com.example.csit314.doctorview.DoctorActivity;
import com.example.csit314.patientview.Patient;
import com.example.csit314.pharmacyview.PharmacyActivity;
import com.example.csit314.prescribe.Prescription;
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
        //the_firebase.push_temp_medication();
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
                    //the_firebase.prepare_test_data();
                    //the_firebase.push_temp_medication();
                    //the_firebase.signIn("theemail1234567@gmail.com", "123456");
                    the_firebase.signIn(user_Email,user_Password);
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
                        launchDoctorActivity();
                    else if(the_firebase.getThe_userType().equals("pharmacist"))
                        launchPharmacyActivity();
                    else
                        launchPatientActivity();
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
    public void launchUserDoctorActivity() //launch to UserAdminActivity
    {
        Intent i = new Intent(this, DoctorActivity.class);
        startActivity(i);
    }
    public void launchPatientActivity() //launch to PatientActivity
    {
        Intent i = new Intent(this, PatientActivity.class);
        Map p = the_firebase.get_pastprescriptionObject(user_Email);
        ArrayList<Prescription> prescriptionAList = new ArrayList<>();
        if(p != null)
        {
            prescriptionAList = collectPrescription(p);
        }
        i.putParcelableArrayListExtra("PrescriptionArrayList", prescriptionAList);
        startActivity(i);
    }
    public void launchDoctorActivity() //launch to DoctorActivity
    {
        Intent i = new Intent(this, DoctorActivity.class);
        ArrayList<Patient> patientAlist;
        Map p = the_firebase.searchUserGroup();
        patientAlist = collectUserAndPrescription(p);
        ArrayList<String> medAlist = new ArrayList<>();
        medAlist = collectMedication();
        i.putParcelableArrayListExtra("DoctorArrayList", patientAlist);
        i.putStringArrayListExtra("medications",medAlist);
        i.putExtra("email",user_Email);
        i.putExtra("password",user_Password);
        startActivity(i);
    }
    public void launchPharmacyActivity() //launch to PharmacyActivity
    {
        Intent i = new Intent(this, PharmacyActivity.class);
        ArrayList<Patient> patientAlist;
        Map p = the_firebase.searchUserGroup();
        patientAlist = collectUserAndPrescription(p);
        i.putParcelableArrayListExtra("PatientArrayList", patientAlist);
        i.putExtra("email",user_Email);
        i.putExtra("password",user_Password);
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

    public ArrayList<Patient> collectUserAndPrescription(Map<String,Object> patient)
    {
        ArrayList<Patient> patientAlist = new ArrayList<>();


        String name;
        String email;
        String number;
        String user_type;
        try {
            for (Map.Entry<String, Object> entry : patient.entrySet()) {
                Map singleUser = (Map) entry.getValue();
                user_type = (String) singleUser.get("user_type");
                name = (String) singleUser.get("name");
                email = entry.getKey().replace("_com", ".com");
                number = (String) singleUser.get("number");
                if (user_type.toLowerCase().equals("patient"))
                    patientAlist.add(new Patient(name, number, email, collectPrescription(the_firebase.get_pastprescriptionObject(email))));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return patientAlist;
    }
    public ArrayList<String> collectMedication()
    {
        Map<String,String> medication = the_firebase.get_medication();
        ArrayList<String> medAlist = new ArrayList<>();

        String name;
        for (Map.Entry<String,String> entry: medication.entrySet())
        {
            medAlist.add(entry.getValue());
        }

        return medAlist;
    }
}