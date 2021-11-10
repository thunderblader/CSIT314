package com.example.csit314.doctorview;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.email.SendMail;
import com.example.csit314.patientview.Patient;
import com.example.csit314.prescribe.Prescription;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;


public class DoctorAddPrescriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button updateBtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner spinner_prescription_name;
    private ArrayList<String> medications;
    private String[] medicationsArray;
    private TextView tv_patient_name;
    private TextView tv_patient_number;
    private TextView tv_patient_email;
    private EditText et_prescription_date;
    private EditText et_prescription_amount;
    private String patient_name;
    private String patient_number;
    private String patient_email;
    private String prescription_name;
    private String prescription_date;
    private String prescription_status;
    private String prescription_amount = "0";
    private String prescription_id;
    private String user_password;
    private String user_email;
    Firebase the_firebase = new Firebase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_add_prescription);

        //Initialize button
        tv_patient_name = (TextView) findViewById(R.id.patient_add_name_doctorTextView);
        tv_patient_number = (TextView) findViewById(R.id.patient_add_number_doctorTextView);
        tv_patient_email = (TextView) findViewById(R.id.patient_add_email_doctorTextView);
        et_prescription_date = (EditText) findViewById(R.id.prescription_add_date_editText);
        et_prescription_amount = (EditText) findViewById(R.id.prescription_add_amount_editText);
        spinner_prescription_name = (Spinner) findViewById(R.id.prescription_add_spinner);

        //get Strings values
        patient_name = (String) getIntent().getStringExtra("patientname");
        patient_number = (String) getIntent().getStringExtra("patientnumber");
        patient_email = (String) getIntent().getStringExtra("patientemail");

        //Set Text
        tv_patient_name.setText( patient_name);
        tv_patient_number.setText(patient_number);
        tv_patient_email.setText(patient_email);
        et_prescription_date.setText(prescription_date);
        et_prescription_amount.setHint("Amount : " + 0);

        //Set Password
        user_email = (String) getIntent().getStringExtra("email");
        user_password = (String) getIntent().getStringExtra("password");

        //MEDICATION Spinner
        medications =(ArrayList<String>) getIntent().getStringArrayListExtra("medications");
        medications = (ArrayList<String>) getIntent().getExtras().getStringArrayList("medications");
        medicationsArray = medications.toArray(new String[medications.size()]);
        prescription_name = medicationsArray[0];
        updateBtn = (Button) findViewById(R.id.addBtn);
        updateBtn.setEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoctorAddPrescriptionActivity.this,android.R.layout.simple_spinner_item,medicationsArray);
        for(String s : medications)
        {
            Log.i("Medications", s);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_prescription_name.setAdapter(adapter);
        spinner_prescription_name.setOnItemSelectedListener(this);

        //Calendar button
        et_prescription_date.setInputType(InputType.TYPE_NULL);
        et_prescription_date.requestFocus();
        et_prescription_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DoctorAddPrescriptionActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = String.valueOf(day) + " " + setMonth(month) + " " + String.valueOf(year);
                et_prescription_date.setText(date);
            }
        };
    }

    public void addOnClick(View view) {
        addPrescription();
    }
    private void launchDoctorViewPatientActivity() {
        Intent intent = new Intent(this, DoctorViewPatientActivity.class);
        ArrayList<Patient> patientAlist;
        Map p = the_firebase.searchUserGroup();
        patientAlist = collectUserAndPrescription(p);

        intent.putParcelableArrayListExtra("DoctorArrayList",patientAlist);
        intent.putStringArrayListExtra("medications",medications);
        intent.putExtra("email",user_email);
        intent.putExtra("password",user_password);
        finish();
        startActivity(intent);
    }
    public void sendEmail() {
        String mEmail = "khoowh1996@gmail.com";
        String mSubject = "Prescription Added";
        String mMessage = "Hi " + patient_name + ",<br><br>Your Prescription has been added by our doctor.<br>To view your prescription kindly login to our app." ;

        Bitmap bitmap;
        QRGEncoder qrgEncoder;
        qrgEncoder = new QRGEncoder(the_firebase.generateRandomstring(69), null, QRGContents.Type.TEXT,200);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        bitmap = qrgEncoder.getBitmap();

        String url = saveImageToExternalStorage(bitmap);
        scanMedia(url);
        SendMail sendMail = new SendMail(this,mEmail,mSubject,mMessage,url);
        sendMail.execute();
        Toast.makeText(getApplicationContext(), "Sending Mail", Toast.LENGTH_LONG).show();
    }

    public static String saveImageToExternalStorage(Bitmap finalBitmap) {
        QRGSaver qrgSaver = new QRGSaver();
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures");
        if (direct.exists ()) direct.delete ();
        try{

            qrgSaver.save(direct.getAbsolutePath(), "qrcode", finalBitmap, QRGContents.ImageType.IMAGE_JPEG);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return direct.getAbsolutePath() + "qrcode" + ".jpg";
    }
    private void scanMedia(String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(scanFileIntent);
    }
    private void addPrescription() {
            prescription_status = "In Progress";
        if(!et_prescription_date.getText().toString().equals(""))
            prescription_date = et_prescription_date.getText().toString();

        if(!et_prescription_amount.getText().toString().equals("") || !et_prescription_amount.getText().toString().equals("0"))
            prescription_amount = et_prescription_amount.getText().toString();
            the_firebase.add_prescription(patient_email,patient_name,prescription_name,prescription_date,prescription_amount,prescription_status);
            Toast.makeText(getApplicationContext(), "Added Prescription, Sending Email...", Toast.LENGTH_LONG).show();
            sendEmail();
            updateBtn.setEnabled(false);
            launchDoctorViewPatientActivity();
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
                date = (String) singlePrescription.get("date");
                if (singlePrescription.get("amount") instanceof String)
                    amount = Long.parseLong((String) "0");
                else
                    amount = (Long) singlePrescription.get("amount");


                if(entry.getKey().equals(prescription_id)){
                    name = prescription_name;
                    date = prescription_date;
                    amount = Long.parseLong(prescription_amount);
                    status = prescription_status;
                }
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
            patientAlist.add(new Patient(name,number,email,collectPrescription(the_firebase.get_pastprescriptionObject(email))));
        }
        return patientAlist;
    }
    private String setMonth(int month)
    {
        String s = "";
        switch(month)
        {
            case 1:
                s = "January";
                break;
            case 2:
                s = "February";
                break;
            case 3:
                s = "March";
                break;
            case 4:
                s = "April";
                break;
            case 5:
                s = "May";
                break;
            case 6:
                s = "June";
                break;
            case 7:
                s = "July";
                break;
            case 8:
                s = "August";
                break;
            case 9:
                s = "September";
                break;
            case 10:
                s = "October";
                break;
            case 11:
                s = "November";
                break;
            case 12:
                s = "December";
                break;

        }
        return s;
    }
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                prescription_name = medicationsArray[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
