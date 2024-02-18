package com.example.hospital_navigation_system;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PatientActivity1 extends AppCompatActivity {
    private String TAG="PATIENT";
    private EditText name, age, ht, wt;
    private Button emgBtn, nonemgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page1);
        Log.d(TAG, "Entered Patient Activity 1");
        name = (EditText) findViewById(R.id.docName);
        age = (EditText) findViewById(R.id.age);
        ht = (EditText) findViewById(R.id.height);
        wt = (EditText) findViewById(R.id.weight);
        emgBtn = (Button) findViewById(R.id.docBtn);
        nonemgBtn = (Button) findViewById(R.id.nonemg);

        emgBtn.setOnClickListener(v -> {
            String p1 = name.getText().toString();
            int p2 = Integer.parseInt(age.getText().toString());
            int height = Integer.parseInt(ht.getText().toString());
            int weight = Integer.parseInt(wt.getText().toString());
            try {
                Log.d(TAG, "Going to emg activity for Patient  - " + p1);
                Intent i1 = new Intent(PatientActivity1.this, Emergency.class);
                i1.putExtra("name", p1);
                i1.putExtra("age", p2);
                i1.putExtra("height", height);
                i1.putExtra("weight", weight);
                startActivity(i1);
            } catch (Exception e) {
                Log.d(TAG, "error at Patient Activity" + e.toString());
            }
        });


        nonemgBtn.setOnClickListener(v -> {
            String p1 = name.getText().toString();
            int p2 = Integer.parseInt(age.getText().toString());
            int height = Integer.parseInt(ht.getText().toString());
            int weight = Integer.parseInt(wt.getText().toString());

            try {
                Log.d(TAG, "Going to next activity for Patient  - " + p1);
                Intent i1 = new Intent(PatientActivity1.this, NonEmergency.class);
                i1.putExtra("name", p1);
                i1.putExtra("age", p2);
                i1.putExtra("height", height);
                i1.putExtra("weight", weight);
                startActivity(i1);
            } catch (Exception e) {
                Log.d(TAG, "error at Patient Activity" + e.toString());
            }
        });

    }
}

