package com.example.hospital_navigation_system;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hospital_navigation_system.database.HospitalDB;
import com.example.hospital_navigation_system.database.PatientDetails;
import java.util.List;

public class PatientDashboard extends AppCompatActivity {
    private static HospitalDB db;
    private static String TAG = "PAT_DB";
    private List<PatientDetails> fetchedResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        Log.d(TAG, "Starting patient dashboard");
        Intent intent = getIntent();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String docName = intent.getStringExtra("docName");
                    Log.d(TAG, "Printing docName" + docName);
                    db = HospitalDB.getInstance(getApplicationContext());
                    if (db != null) {
                        fetchedResults = db.hDao2().fetchPatientDetails(docName);
                        Log.d(TAG, "Fetched Results " + fetchedResults);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListView listViewPatients = findViewById(R.id.listViewPatients);
                            PatientListAdapter adapter = new PatientListAdapter(PatientDashboard.this, R.layout.patient_card_item, fetchedResults);
                            listViewPatients.setAdapter(adapter);
                        }
                    });
                }
                catch (Exception e){
                    Log.e(TAG, "Error in adding details to DB"+ e);
                }
            }
        });
        thread.start();
    }
}
