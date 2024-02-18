package com.example.hospital_navigation_system.services;

// importing all the classes

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

/*
 created service class that implements the smartphone accelerometer sensor
 for Respiratory Rate computation
*/
public class RespiratoryRateService extends Service implements SensorEventListener{
    public RespiratoryRateService() {
    }
    // declaring Accelerometer sensor parameters
    private SensorManager accelManager;
    private Sensor senseAccel;
    private ArrayList<Float> accelValuesX = new ArrayList<>();
    private ArrayList<Float> accelValuesY = new ArrayList<>();
    private ArrayList<Float> accelValuesZ = new ArrayList<>();
    private int RespiratoryRateTimeLimit = 45000; // setting time limit for Resp Rate service

    // initializing the sensor parameters
    @Override
    public void onCreate(){
        Log.i("RESP_TAG", "onCreate: Measuring Respiratory Rate");
        accelManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senseAccel = accelManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelManager.registerListener((SensorEventListener) this, senseAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // clearing the accelerometer values across all axes (X,Y,Z)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("RESP_TAG", "onStartCommand: Measuring Respiratory Rate");
        accelValuesX.clear();
        accelValuesY.clear();
        accelValuesZ.clear();
        return START_STICKY;
    }

    // Setting and starting the count down timer for the Resp Rate Service
    // adding the accelValues across 3 dimensions
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        CountDownTimer RespiratoryRateTimer = new RespiratoryRateTimer(RespiratoryRateTimeLimit, 1000);
        RespiratoryRateTimer.start();
        Sensor genericSensor = sensorEvent.sensor;
        if (genericSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelValuesX.add((sensorEvent.values[0]));
            accelValuesY.add((sensorEvent.values[1]));
            accelValuesZ.add((sensorEvent.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    /* Upon completion of 45s, the accelValues are used to compute
       Resp Rate using given helper code*/
    @Override
    public void onDestroy(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("RESP_TAG", "onDestroy: Measuring Respiratory Rate");
//                Log.d("RESP_TAG", "Length of accelValuesX, Y, Z" + accelValuesX.size() + accelValuesY.size() + accelValuesZ.size());
                Log.d("RESP_TAG", "run: accelValuesX " + accelValuesX.get(0) + " accelValuesY " + accelValuesY.get(0) + " accelValuesZ " + accelValuesZ.get(0) );

                // computing
                int respRate = callRespiratoryCalculator(accelValuesX, accelValuesY, accelValuesZ);
                Log.d("RESP_TAG", "Resp rate using helper code is "+ respRate);
                //Unregisters accelerometer sensor data listener
                accelManager.unregisterListener(RespiratoryRateService.this);

                Log.d("breathingRate", String.valueOf(respRate));
                Log.i("service", "Service stopping");

                /* Using LocalBroadCast Manager to send the respRate value to Main Activity */
                Intent intent = new Intent("broadCastRespData");
                Bundle b = new Bundle();
                b.putFloat("respRate",respRate);
                intent.putExtras(b);
                LocalBroadcastManager.getInstance(RespiratoryRateService.this).sendBroadcast(intent);
            }
        });
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }


    public class RespiratoryRateTimer extends CountDownTimer {

        public RespiratoryRateTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("onTick", String.valueOf(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            stopSelf();
        }
    }

    private int callRespiratoryCalculator(ArrayList<Float> accelValuesX, ArrayList<Float> accelValuesY, ArrayList<Float> accelValuesZ  ) {
        Log.d("RESP_TAG", "callRespiratoryCalculator: measuring resp rate");
        float previousValue = 0f;
        float currentValue = 0f;
        previousValue = 10f;
        int k = 0;
        int end = Math.min(Math.min(accelValuesX.size(), accelValuesY.size()), accelValuesZ.size());
        for (int i = 11; i <= end-1; i++) {
            double temp1 = Math.pow(accelValuesZ.get(i), 2.0);
            double temp2 = Math.pow(accelValuesX.get(i), 2.0);
            double temp3 = Math.pow(accelValuesY.get(i), 2.0);
            currentValue = (float) Math.sqrt(temp1 + temp2 + temp3);
            if (Math.abs(previousValue - currentValue) > 0.15) {
                k++;
            }
            previousValue = currentValue;
        }
        double ret = k / 45.00;
        return (int) (ret * 30);
    }

}
