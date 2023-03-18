package com.example.dreamstar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;



public class MainActivity extends AppCompatActivity {

    private EditText txtMobile;
    private EditText txtMessage;
    private EditText txtCount;
    private Button btnSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        txtMobile = (EditText)findViewById(R.id.mblTxt);
        txtMessage = (EditText)findViewById(R.id.msgTxt);
        txtCount = (EditText) findViewById(R.id.msgCount);
        btnSms = (Button)findViewById(R.id.btnSend);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    String mobileNumber = txtMobile.getText().toString();
                    String message = txtMessage.getText().toString();
                    String count = txtCount.getText().toString();

                    HttpFileReader task = new HttpFileReader();
                    String key = task.execute(mobileNumber,message,count).get();

//                    System.out.println("Input keycode : "+key);
//
//                        for (int i = 0; i < Integer.parseInt(txtCount.getText().toString());i++){
//
//                            smgr.sendTextMessage(txtMobile.getText().toString(),null,txtMessage.getText().toString(),null,null);
//                            Toast.makeText(MainActivity.this, "Sent "+(i+1)+ " Successfully", Toast.LENGTH_SHORT).show();
//                            Random random = new Random();
//                            int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
//                            Thread.sleep(randomNumber * 1000);
//                    }
                }
                catch (Exception e){
//                    Toast.makeText(MainActivity.this, "Insufficient account Balance", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    System.exit(1);
                }
            }
        });

    }
}

class HttpFileReader extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... params) {

        String mobileNumber = params[0];
        String message = params[1];
        String count = params[2];

        int minValue = 5;
        int maxValue = 10;

        SmsManager smgr = SmsManager.getDefault();

        try {

            for (int i = 0; i < Integer.parseInt(count);i++){

                smgr.sendTextMessage(mobileNumber,null,message,null,null);
                Thread.sleep( 1000);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    protected void onPostExecute(String result) {
        // Handle the result here
        System.out.println(result);
    }
}