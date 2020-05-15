package com.example.sosapp;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int reqCode = 1;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECORD_AUDIO
        }, reqCode);

        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != reqCode || grantResults.length < 2 || grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECORD_AUDIO
            }, reqCode);
        }
        startVoice();
    }

    public void doAction() {
        // Play alarm
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            }
        });

        // Send SMS
        SmsManager sms = SmsManager.getDefault();
        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String name, phone, message;

        for(int i=0;i<5;i++)
        {
            phone = spref.getString("phone" + String.valueOf(i+1), null);
            name = spref.getString("name" + String.valueOf(i+1), null);
            message = "Hello " + name + ", I am in distress! Need your help. Please call me soon!";
            if(phone != null && name != null)
            {
                sms.sendTextMessage(phone, null, message, null, null);
            }
        }
    }

    private void startVoice() {
        if(!checkServiceRunning())
        {
            startService(new Intent(this, VoiceService.class));
        }
    }

    private boolean checkServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                    Integer.MAX_VALUE)) {
                if (getString(R.string.my_service_name).equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
