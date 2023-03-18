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
    private EditText txtKey;
    private Button btnSms;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        txtMobile = (EditText)findViewById(R.id.mblTxt);
        txtMessage = (EditText)findViewById(R.id.msgTxt);
        txtCount = (EditText) findViewById(R.id.msgCount);
        txtKey = (EditText) findViewById(R.id.apiKey);
        btnSms = (Button)findViewById(R.id.btnSend);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    SmsManager smgr = SmsManager.getDefault();
                    int minValue = 5;
                    int maxValue = 10;
                    String inputKey = txtKey.getText().toString();


                    HttpFileReader task = new HttpFileReader();

                        key = task.execute("https://raw.githubusercontent.com/wmhchathuranga/dreamStar-App/main/API.key").get();
                        System.out.println("Input keycode : "+key);

                    if(inputKey.equals(key.toString())){
                        for (int i = 0; i < Integer.parseInt(txtCount.getText().toString());i++){
                        smgr.sendTextMessage(txtMobile.getText().toString(),null,txtMessage.getText().toString(),null,null);
                        Toast.makeText(MainActivity.this, "Sent "+i+ " Successfully", Toast.LENGTH_SHORT).show();
                        Random random = new Random();
                        int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
                        Thread.sleep(randomNumber * 1000);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid API key!", Toast.LENGTH_SHORT).show();
                    }

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
    protected String doInBackground(String... urls) {
        String result = "";
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(String result) {
        // Handle the result here
        System.out.println(result);
    }
}