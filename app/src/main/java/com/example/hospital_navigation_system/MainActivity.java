package com.example.hospital_navigation_system;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital_navigation_system.database.Doctor;
import com.example.hospital_navigation_system.database.HospitalDB;


public class MainActivity extends AppCompatActivity {

    private Button buttonDoctor, buttonPatient, buttonMisc;
    private static HospitalDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    db = HospitalDB.getInstance(getApplicationContext());
//                    docToDB(db);
//                }
//                catch (Exception e){
//                    Log.e("MAIN_ACT", "Error in adding details to DB");
//                }
//            }
//        });
//        thread.start();
        buttonDoctor = findViewById(R.id.doctor);
        buttonPatient = findViewById(R.id.patient);
        buttonMisc = findViewById(R.id.misc);

        buttonDoctor.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DoctorActivity.class)));

        buttonPatient.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PatientActivity1.class)));

        buttonMisc.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MiscActivity.class)));
    }

    private void docToDB(HospitalDB db){

        Doctor data = new Doctor();
        data.doctorName = "Robert Parkins";
        data.specialization = "Accidents";
        data.isOnCall = 1;
        db.hDao2().insertDoctor(data);

        Doctor data2 = new Doctor();
        data2.doctorName = "Josh Anderson";
        data2.specialization = "Accidents";
        data2.isOnCall = 0;
        db.hDao2().insertDoctor(data2);

        Doctor data3 = new Doctor();
        data3.doctorName = "John Smith";
        data3.specialization = "Allergies";
        data3.isOnCall = 0;
        db.hDao2().insertDoctor(data3);

        Doctor data4 = new Doctor();
        data4.doctorName = "Jane Doe";
        data4.specialization = "Allergies";
        data4.isOnCall = 1;
        db.hDao2().insertDoctor(data4);

        Doctor data5 = new Doctor();
        data5.doctorName = "Ron Swanson";
        data5.specialization = "Stroke";
        data5.isOnCall = 1;
        db.hDao2().insertDoctor(data5);

        Doctor data6 = new Doctor();
        data6.doctorName = "Ann Perkins";
        data6.specialization = "Stroke";
        data6.isOnCall = 0;
        db.hDao2().insertDoctor(data6);

        Doctor data7 = new Doctor();
        data7.doctorName = "Leslie Knope";
        data7.specialization = "Covid";
        data7.isOnCall = 1;
        db.hDao2().insertDoctor(data7);

        Doctor data8 = new Doctor();
        data8.doctorName = "Donna Dunphy";
        data8.specialization = "Covid";
        data8.isOnCall = 0;
        db.hDao2().insertDoctor(data8);

        Doctor data9 = new Doctor();
        data9.doctorName = "Amy Santiago";
        data9.specialization = "ENT";
        data9.isOnCall = 0;
        db.hDao2().insertDoctor(data9);

        Doctor data10 = new Doctor();
        data10.doctorName = "Jake Peralta";
        data10.specialization = "ENT";
        data10.isOnCall = 1;
        db.hDao2().insertDoctor(data10);

        Doctor data11 = new Doctor();
        data11.doctorName = "Ray Holt";
        data11.specialization = "General";
        data11.isOnCall = 1;
        db.hDao2().insertDoctor(data11);

        Doctor data12 = new Doctor();
        data12.doctorName = "Terry Crews";
        data12.specialization = "General";
        data12.isOnCall = 0;
        db.hDao2().insertDoctor(data12);

    }
}

