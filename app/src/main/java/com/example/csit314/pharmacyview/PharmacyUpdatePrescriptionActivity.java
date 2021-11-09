package com.example.csit314.pharmacyview;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.databinding.ActivityPrescriptionBinding;
import com.example.csit314.email.SendMail;
import com.example.csit314.patientview.Patient;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.PrescriptionActivity;
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
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class PharmacyUpdatePrescriptionActivity extends AppCompatActivity {
    private Button updateBtn;
    private TextView tv_patient_name;
    private TextView tv_patient_number;
    private TextView tv_patient_email;
    private TextView tv_prescription_name;
    private TextView tv_prescription_date;
    private TextView tv_prescription_amount;
    private TextView tv_prescription_status;
    private String patient_name;
    private String patient_number;
    private String patient_email;
    private String prescription_name;
    private String prescription_date;
    private String prescription_amount;
    private String prescription_status;
    private String prescription_id;
    private String user_password;
    private String user_email;
    Firebase the_firebase = new Firebase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_update_prescription);
        tv_patient_name = (TextView) findViewById(R.id.patient_name_textview);
        tv_patient_number = (TextView) findViewById(R.id.patient_number_textview);
        tv_patient_email = (TextView) findViewById(R.id.patient_email_textview);
        tv_prescription_name = (TextView) findViewById(R.id.prescription_name_textview);
        tv_prescription_date = (TextView) findViewById(R.id.prescription_date_textview);
        tv_prescription_amount = (TextView) findViewById(R.id.prescription_amount_textview);
        tv_prescription_status = (TextView) findViewById(R.id.prescription_status_textview);
        patient_name = (String) getIntent().getStringExtra("patientname");
        patient_number = (String) getIntent().getStringExtra("patientnumber");
        patient_email = (String) getIntent().getStringExtra("patientemail");
        prescription_name = (String) getIntent().getStringExtra("prescriptionname");
        prescription_date = (String) getIntent().getStringExtra("prescriptiondate");
        prescription_amount = (String) getIntent().getStringExtra("prescriptionamount");
        prescription_status = (String) getIntent().getStringExtra("prescriptionstatus");
        prescription_id = (String) getIntent().getStringExtra("prescriptionid");
        tv_patient_name.setText(patient_name);
        tv_patient_number.setText(patient_number);
        tv_patient_email.setText(patient_email);
        tv_prescription_name.setText(prescription_name);
        tv_prescription_date.setText(prescription_date);
        tv_prescription_amount.setText(prescription_amount);
        tv_prescription_status.setText(prescription_status);
        user_email = (String) getIntent().getStringExtra("email");
        user_password = (String) getIntent().getStringExtra("password");
        updateBtn = (Button) findViewById(R.id.updateBtn);
        if(prescription_status.equals("in progress"))
            updateBtn.setEnabled(true);
        else
            updateBtn.setEnabled(false);

    }

    public void updateOnClick(View view) {
        updatePrescription();
        sendEmail();
        launchPharmacyViewPrescriptionActivity();
    }
    private void launchPharmacyViewPrescriptionActivity() {
        Intent intent = new Intent(this,PharmacyViewPrescriptionActivity.class);
        ArrayList<Patient> patientAlist;
        Map p = the_firebase.searchUserGroup();
        patientAlist = collectUserAndPrescription(p);
        intent.putParcelableArrayListExtra("PatientArrayList",patientAlist);
        intent.putExtra("email",user_email);
        intent.putExtra("password",user_password);
        finish();
        startActivity(intent);
    }

    private void sendEmail(){
        String mEmail = "khoowh1996@gmail.com";
        String mSubject = "Prescription Completed";
        String mMessage = "Hi " + patient_name + "," ;
        SendMail sendMail = new SendMail(this,mEmail,mSubject,mMessage);
        sendMail.execute();
    }

    private void updatePrescription() {
        prescription_status = "completed";
        tv_prescription_status.setText(prescription_status);
        the_firebase.edit_prescription2(patient_email,prescription_status,prescription_id);
        Toast.makeText(getApplicationContext(),"Updated Prescription, Sending Email...",Toast.LENGTH_LONG).show();
    }
    protected void onStart()
    {
        super.onStart();
        the_firebase.signIn(user_email,user_password);
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
                if(entry.getKey().equals(prescription_id))
                    status = prescription_status;
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
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
