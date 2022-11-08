package com.tworoot2.notificationappalaram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button alarmbutton, cancelButton;
    EditText text;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmbutton = (Button) findViewById(R.id.button);
        cancelButton = (Button) findViewById(R.id.button2);
        text = (EditText) findViewById(R.id.editText);


        Intent intent = getIntent();
        if(intent!=null) {
            String extraKey = intent.getStringExtra("key");
            notification(extraKey);
            Toast.makeText(this, ""+extraKey, Toast.LENGTH_SHORT).show();
        }else {

        }

        alarmbutton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    private void notification(String extraKey) {
        if (extraKey!=null){
            int i = Integer.parseInt(extraKey);
            intent = new Intent(this, MyBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + (i * 1000), 10000, pendingIntent);
            Toast.makeText(this, "Alarm will set in " + i + " seconds", Toast.LENGTH_LONG).show();
        }
    }

    // This method to be called at Start button click and set repeating at every 10 seconds interval
    public void startAlert(View view) {
        if (!text.getText().toString().equals("")) {
            int i = Integer.parseInt(text.getText().toString());
            intent = new Intent(this, MyBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + (i * 1000), 10000, pendingIntent);
            Toast.makeText(this, "Alarm will set in " + i + " seconds", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Please Provide time ",Toast.LENGTH_SHORT).show();
        }
    }
/*    public void startAlertAtParticularTime() {

        // alarm first vibrate at 14 hrs and 40 min and repeat itself at ONE_HOUR interval

        intent = new Intent(this, MyBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Log.e("TAG", "startAlertAtParticularTime: " );
        String[] separated = currentTime.split(":");
        String Hr = separated[0];
        String Mint = separated[1];
        String Sec = separated[2];

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Hr));
        calendar.set(Calendar.MINUTE, Integer.parseInt(Mint));
        calendar.set(Calendar.SECOND, Integer.parseInt(Sec)+2);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getInstance().getTimeInMillis() + (Integer.parseInt(Sec) * 1000), 10000, pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
        Toast.makeText(this, "Alarm will vibrate at time specified", Toast.LENGTH_SHORT).show();
    }*/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            startAlert(v);
        } else {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Alarm Disabled !!",Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

//    public void szoom(View view) {
//        startAlertAtParticularTime();
//    }

}