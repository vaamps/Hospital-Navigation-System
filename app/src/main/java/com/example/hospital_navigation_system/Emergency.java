package com.example.hospital_navigation_system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital_navigation_system.database.Doctor;
import com.example.hospital_navigation_system.database.HospitalDB;
import com.example.hospital_navigation_system.database.Patient;
import com.example.hospital_navigation_system.services.RoomAllocationService;

import org.chromium.base.task.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Emergency extends AppCompatActivity {
    JSONObject symptomsData = new JSONObject();
    private static String TAG = "EMG";
    public HospitalDB db;
    private Patient data = new Patient();
    private String name;
    private int age;
    private float heartRate , respRate;
    private int height = 0, weight = 0;
    private String selectedItem;
    private EditText start, end, hr, rr;
    private TextView arrTime;
    private String sp, ep;
    private String apiKey = "AIzaSyBDq1__u3V_05HRUe9IpQYuEnu7gMpQDXI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            // setting the initial parameters for the symptoms and adding the computed rates from PatientActivity1
            Intent intent = getIntent();
            name = intent.getStringExtra("name");
            age = intent.getIntExtra("age", 0);
            height = intent.getIntExtra("height", 0);
            weight = intent.getIntExtra("weight", 0);

            //Setting up values for Patient to insert in DB
            symptomsData.put("Stroke",0);
            symptomsData.put("Covid",0);
            symptomsData.put("Accident",0);
            symptomsData.put("Allergies",0);
            symptomsData.put("heartRate", heartRate);
            symptomsData.put("respRate", respRate);
            symptomsData.put("name", name);
            symptomsData.put("age", age);
            symptomsData.put("height", height);
            symptomsData.put("weight", weight);

            Log.d(TAG, "Data " + symptomsData);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emg);

        // extracting form values
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        hr = findViewById(R.id.patientHR);
        rr = findViewById(R.id.patientRR);
        arrTime = findViewById(R.id.arrivalTime);

        // setting the heart and resp rate values
        heartRate = Float.valueOf(hr.getText().toString());
        respRate = Float.valueOf(rr.getText().toString());

        Spinner spinner = findViewById(R.id.symptoms);
        Button submitButton = (Button) findViewById(R.id.symptomsubmit);
        Button computeButton = (Button) findViewById(R.id.computeTT);

        String[] items = new String[]{"None","Accident","Covid","Allergies","Stroke"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d(TAG,"Creating DB instance" );
                    db = HospitalDB.getInstance(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (String) adapterView.getItemAtPosition(i);
                Log.d(TAG, "Selected item" + selectedItem);

                try {
                    if (selectedItem != "None")
                        symptomsData.put(selectedItem, 1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting start and end points to compute
                sp = start.getText().toString();
                ep = end.getText().toString();
                Log.d(TAG, "Start and end points - " + sp + ep);
                new Thread(new ComputeRoadConditionTask()).start();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db = HospitalDB.getInstance(getApplicationContext());
                    data.stroke = (int) symptomsData.get("Stroke");
                    data.accident = Integer.valueOf(symptomsData.get("Accident").toString());
                    data.covid = Integer.valueOf(symptomsData.get("Covid").toString());
                    data.allergies = Integer.valueOf(symptomsData.get("Allergies").toString());

                    data.patientName= symptomsData.get("name").toString();
                    data.age = (int) symptomsData.get("age");
                    data.height = (int) symptomsData.get("height");
                    data.weight = (int) symptomsData.get("weight");
                    data.heartRate = heartRate;
                    data.respRate = respRate;

                    // INSERTING PATIENT DETAILS TO DB
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("DB_TAG","Printing DB" + data.patientName + data.age + data.height + data.weight);
                            try {
                                db = HospitalDB.getInstance(getApplicationContext());
                                if (db != null) {
                                    Log.d(TAG, "Inserting patient form data in DB");
                                    db.hDao2().insertPatient(data);
                                }
                            }
                            catch (Exception e){
                                Log.e(TAG, "Error in adding details to DB");
                            }
                        }
                    });
                    thread.start();

                    // allocating room to the patient
                    Log.d(TAG, "Selected Item - " + selectedItem);
                    String result = allocateRoom(selectedItem);

                    // Allocating doctor to the patient and storing details in DB
                    Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int docID;
                            docID = allocateDoctor(selectedItem);
                            try {
                                db = HospitalDB.getInstance(getApplicationContext());
                                int pID = db.hDao2().fetchPatientID(data.patientName);
                                Log.d("DOC_ALLOC", "INSERTING DATA INTO DPLNK");
                                db.hDao2().insertDPLink(pID, docID);
                            }
                            catch (Exception e){
                                Log.e("DOC_ALLOC", "ERROR IN ADDING DATA INTO DPLNK" + e.toString());
                            }
                        }
                    });
                    thread2.start();

                    // Showing Ambulance staff where to take patient
                    Toast.makeText(Emergency.this, "Please get the patient to " + result, Toast.LENGTH_SHORT).show();
                    goToNavigationActivity(view);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private String allocateRoom(String symptom) throws JSONException {
        // Your logic for allocating the room goes here
        if ("Accident".equals(symptom)) {
            return "CE 1";
        } else if ("Stroke".equals(symptom)) {
            return "CE 4";
        } else if ("Allergies".equals(symptom)) {
            return "CE 8";
        } else if ("Covid".equals(symptom)) {
            return "Treatment Room 1";
        }
        else
            return "";
    }

    private int allocateDoctor(String symptom) {
        // Logic for allocating the doctor goes here
        db = HospitalDB.getInstance(getApplicationContext());
        Doctor avlDoc = new Doctor();
        avlDoc = db.hDao2().fetchOnCallDoctor(symptom);
        if (avlDoc != null)
            return avlDoc.docId;
        else
            return -1;
    }

    public void goToNavigationActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        // replace this with Non Emergency Navigation Class
        //navigation_main.class + navigation_main2.class Reference to current code
        startActivity(intent);
    }

    private class ComputeRoadConditionTask implements Runnable {

        @Override
        public void run() {
            String url = "https://maps.googleapis.com/maps/api/directions/json?"
                    + "origin=" + sp
                    + "&destination=" + ep
                    + "&mode=driving"
                    + "&departure_time=now"
                    + "&key=" + apiKey; // Replace with your API key

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                JSONObject json = new JSONObject(response.toString());
                Log.i("ARR_TIME", "Response JSON " + json);
                String curTravelTime = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONArray("legs")
                        .getJSONObject(0)
                        .getJSONObject("duration_in_traffic")
                        .getString("text");
                Log.d("ARR", "Arrival time" + curTravelTime);

                // Use a Handler to update the UI on the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        arrTime.setText(curTravelTime);
                    }
                });
            } catch (Exception e) {
                Log.e("ERROR", "Exception: " + e);

                // Use a Handler to update the UI on the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        arrTime.setText("Failed to compute arrival time");
                    }
                });
            }
        }
    }

}
