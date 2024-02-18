package com.example.hospital_navigation_system;

// Importing all the required classes
import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.hospital_navigation_system.database.HospitalDB;
import com.example.hospital_navigation_system.database.Patient;
import com.example.hospital_navigation_system.services.RespiratoryRateService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Sensor Activity class (presents sensor activity of project)
public class SensorActivity extends AppCompatActivity {

    TextureView textureView;
    private static final SparseIntArray ORIENTATION = new SparseIntArray();
    static {
        ORIENTATION.append(Surface.ROTATION_0,90);
        ORIENTATION.append(Surface.ROTATION_90,0);
        ORIENTATION.append(Surface.ROTATION_180,270);
        ORIENTATION.append(Surface.ROTATION_270,180);
    }

    private String cameraId;
    CameraDevice cameraDevice;
    CameraCaptureSession cameraCaptureSession;
    CaptureRequest captureRequest;
    CaptureRequest.Builder captureRequestBuilder;

    private Size imageDimensions;
    Handler handler;
    HandlerThread handlerThread;
    CountDownTimer startTimer;
    TextView estartTimertext;
    TextView svcDescription;
    TextView heartRateText;

    TextView respRateText;

    public boolean heartRateMeasure=false;
    public boolean respRateMeasure = false;
    public boolean cameraFlashOn = false;
    public boolean isSvcRunning = false;
    public boolean setTextureView = true;
    public float heartRate =0;
    public float respRate =0;
    private long [] timeArray;
    HeartRateTimer heartRateSvcTimer;
    public int heartRateSvcTimeLimit = 45000;
    long initialTime = System.currentTimeMillis();
    ArrayList<Bitmap> frameList = new ArrayList<>();
    String heartRateVal;
    long captureDuration = 45 * 1000; // Capture frames for 30 seconds
    public HospitalDB db;
    private Patient data = new Patient();
    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Intent intent = getIntent();
        patientName = intent.getStringExtra("pname");

        /* creating and initializing new DB instance */
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    db = HospitalDB.getInstance(getApplicationContext());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();

        // Initializing all XML view and button elements
        textureView = (TextureView)findViewById(R.id.cameraView);
        textureView.setSurfaceTextureListener(textureListener);
        timeArray = new long [30];
        svcDescription =  findViewById(R.id.serviceDesc);
        heartRateText = findViewById(R.id.heartRateText);
        respRateText = findViewById(R.id.respRateText);
        Button respRate = (Button) findViewById(R.id.respRateBtn);
        Button heartRateButton = (Button) findViewById(R.id.heartRateBtn);
        Button uploadSymptomsButton = (Button) findViewById(R.id.submit);
//        Button rcButton = (Button) findViewById(R.id.rcBtn);

        respRate.setEnabled(false); // disabling respRate button
        uploadSymptomsButton.setEnabled(false); // disabling the uploadSymptoms button
        svcDescription.setText("Please select the Measure Heart Rate button");

        // setting the onClick event for HeartRate Button
        heartRateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!isSvcRunning){
//                    textureView.setVisibility(View.VISIBLE); // Enable

                    isSvcRunning =true;
                    svcDescription.setText("Currently measuring Heart Rate");
                    Log.d("HEART_TAG", "onClick: click heart rate button");
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    cameraFlashOn = true; // turning on camera flash
                    try {
                        updatePreview();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    // starting the service timer for heart rate
                    startTimer = new serviceStartTimer(3000, 1000);
                    startTimer.start();

                }
                else{
                    Toast.makeText(SensorActivity.this, "Another Service is running", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // setting the onClick event for the Resp Rate Button
        respRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSvcRunning){
                    textureView.setVisibility(View.INVISIBLE); // disabling visibility for Respiratory activity
                    isSvcRunning =true;
                    respRateMeasure =true;
                    svcDescription.setText("Currently measuring Respiratory Rate");
                    Log.d("RESP_TAG", "onClick: click resp rate button");
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    // starting the service timer for resp rate
                    startTimer = new serviceStartTimer(5000, 1000);
                    startTimer.start();
                    // creating an intent and calling the RespiratoryRateService
                    Intent respService = new Intent(SensorActivity.this, RespiratoryRateService.class);
                    startService(respService);
                    uploadSymptomsButton.setEnabled(true);  // enabling the uploadSymptoms Button
                }
                else{
                    Toast.makeText(SensorActivity.this, "Another Service is running", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textureView.setVisibility(View.VISIBLE); // resetting the textureView Visibility
        // using this class to extract data from Respiratory service and use it in Main Activity
        LocalBroadcastManager.getInstance(SensorActivity.this).registerReceiver(new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent){
                isSvcRunning =false;
                respRateMeasure =false;
                Bundle b = intent.getExtras();
                SensorActivity.this.respRate = b.getFloat("respRate");
                Log.d("RESP_TAG", "respRate is " + String.valueOf(SensorActivity.this.respRate));
                svcDescription.setText("");
                respRateText.setText(String.valueOf(SensorActivity.this.respRate));
//                getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                b.clear();
                System.gc();
                Button uploadSymptomsButton = (Button) findViewById(R.id.submit);
                uploadSymptomsButton.setEnabled(true);
            }
        }, new IntentFilter("broadCastRespData"));

        // setting the onClick event for the Next Button
        uploadSymptomsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting data parameters before inserting into DB
                data.respRate = SensorActivity.this.respRate;
                data.heartRate = SensorActivity.this.heartRate;

                // inserting the data into DB using new thread
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db = HospitalDB.getInstance(getApplicationContext());

                        if(db != null){
                            data = db.hDao2().fetchPatientByName(patientName);
                            db.hDao2().updatePatient(data);
                        }

                        // going back to main activity
                        goToNavigationActivity(view);

//                        goToSymptomActivity(view, data.heartRate , data.respRate);
                    }
                });
                thread.start();
                Toast.makeText(SensorActivity.this, "Please wait, as the doctor will see you soon. ", Toast.LENGTH_SHORT).show();
            }
        });

        // GMaps implementation
//        rcButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SensorActivity.this, Activity3.class);
//                startActivity(intent);
//
//            }
//        });


    }

    /* created basic Count Down Timer to make the user aware of that the service will start soon
       Also used to track the service time which is 45s for our project  */
    public class serviceStartTimer extends CountDownTimer {

        public serviceStartTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            estartTimertext =  findViewById(R.id.serviceTimer);
            estartTimertext.setText("Starting the task in : " + millisUntilFinished / 1000 + " seconds.");

        }

        @Override
        public void onFinish() {
            estartTimertext.setText("");
            // Initiating the first service, Heart Rate Service
            if (!respRateMeasure){
                heartRateMeasure = true;
                heartRateSvcTimer = new HeartRateTimer(heartRateSvcTimeLimit, 1000);
                heartRateSvcTimer.start();
            }
        }
    }

    public class HeartRateTimer extends CountDownTimer {

        public HeartRateTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            estartTimertext =  findViewById(R.id.serviceTimer);
            estartTimertext.setText("Heart Rate Compute Timer  " + millisUntilFinished / 1000);
            Log.d("HEART_TAG","Count Down for Heart Rate Service : " + millisUntilFinished / 1000);
        }

        // Helper code provided to compute the Heart Rate based on video frames
        protected String heartRateCompute2(ArrayList<Bitmap> frameList) {
            List<Long> a = new ArrayList<>();
            for (Bitmap bitmap : frameList) {
                long redBucket = 0;
                long pixelCount = 0;
                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();
                for (int y = 550; y < bitmapHeight; y++) {
                    for (int x = 550; x < bitmapWidth; x++) {
                        int c = bitmap.getPixel(x, y);
                        pixelCount++;
                        redBucket += Color.red(c) + Color.blue(c) + Color.green(c);
                    }
                }
                a.add(redBucket);
            }
            List<Long> b = new ArrayList<>();
            for (int i = 0; i < a.size() - 5; i++) {
                long temp =
                        (a.get(i) +
                                a.get(i + 1) +
                                a.get(i + 2) +
                                a.get(i + 3) +
                                a.get(i + 4)) / 4;
                b.add(temp);
            }
            if (!b.isEmpty()) {
                long x = b.get(0);
                int count = 0;
                for (int i = 1; i < b.size() - 1; i++) {
                    long p = b.get(i);
                    if ((p - x) > 3500) {
                        count++;
                    }
                    x = b.get(i);
                }
                int rate = (int) (((float) count / 45) * 60);
                return Integer.toString(rate);
            }
            else {
                return "0";
            }
        }

        /* Upon completion of Heart Rate Service Timer (at end of 45s),
        we need to compute the Heart rate for given video frames */
        @Override
        public void onFinish() {
            Log.d("HEART_TAG", "onFinish: Printing frames length " + frameList.size());
            heartRateVal = heartRateCompute2(frameList);
            heartRate = Float.valueOf(heartRateVal);
            Log.d("HEART_TAG", "Heart Rate using helper code is " + heartRateVal);

            estartTimertext.setText("");
            // resetting the service parameters for next computation process
            heartRateMeasure = false;
            isSvcRunning =false;
            cameraFlashOn =false;
            try {
                updatePreview();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Button respRate = findViewById(R.id.respRateBtn);
            respRate.setEnabled(true); // enabling button to compute Respiratory Rate
            heartRateText.setText(heartRateVal);
            svcDescription.setText("Please select the Measure Respiratory Rate Button");

        }
    }

    public void goToSymptomActivity(View view, float heartRate, float respRate) {
        Intent intent = new Intent(this, NonEmergency.class);
        intent.putExtra("hRate", heartRate);
        intent.putExtra("rRate", respRate);
        startActivity(intent);
    }

    public void goToNavigationActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("hRate", heartRate);
//        intent.putExtra("rRate", respRate);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(getApplicationContext(),"Apologies, but the Camera Permission is necessary!",Toast.LENGTH_LONG).show();
            }
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
            try {
                opencamera();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
            /* if the Heart rate service is enabled,
            then collect all the frames in texture view into a list */
            if (heartRateMeasure){
                if (System.currentTimeMillis() - initialTime < captureDuration) {
                    Bitmap frame = textureView.getBitmap();
                    frameList.add(frame);
                    Log.d("HEART_TAG", "onSurfaceTextureUpdated: Computing frameList length" + frameList.size());
                }
            }
        }
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camDevice) {
            cameraDevice = camDevice;
            try {
                createCameraPreview();
            } catch (CameraAccessException e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            if (cameraDevice != null)
                cameraDevice.close();
            cameraDevice = null;
        }
    };

    public  void createCameraPreview() throws CameraAccessException {
        SurfaceTexture texture = textureView.getSurfaceTexture();
        texture.setDefaultBufferSize(imageDimensions.getWidth(),imageDimensions.getHeight());
        Surface surface = new Surface(texture);
        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        captureRequestBuilder.addTarget(surface);

        cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession camCaptureSession) {
                if (cameraDevice == null){
                    return;
                }
                cameraCaptureSession = camCaptureSession;
                try {
                    updatePreview();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                Toast.makeText(getApplicationContext(),"Configuration Changed", Toast.LENGTH_LONG).show() ;
            }
        }, null);
    }

    private void updatePreview() throws CameraAccessException {
        if (cameraDevice == null){
            return;
        }
        captureRequestBuilder.set(captureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        if (cameraFlashOn){
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        }
        else{
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
        }
        cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(),null, handler);
    }
    private void opencamera() throws CameraAccessException {
        CameraManager manager =(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        cameraId = manager.getCameraIdList()[0];
        CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        imageDimensions = map.getOutputSizes(SurfaceTexture.class)[0];
        if ( ActivityCompat.checkSelfPermission(this,permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},50);

        }
        manager.openCamera(cameraId, stateCallback,null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if (textureView.isAvailable()){
            try {
                opencamera();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        else{
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    private void startBackgroundThread() {
        handlerThread = new HandlerThread("Camera Background");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

}
