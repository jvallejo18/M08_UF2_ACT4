package com.example.suitemultimedia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

public class SonidoActivity extends AppCompatActivity {

    ToggleButton audiorecord;
    MediaRecorder mediaRecorder;
    MediaPlayer player;
    Button playButton;
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    File file = new File (path, "myAudio.3gp");

    int contRecorder = 1;
    int contPlayer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonido);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

        mediaRecorder = new MediaRecorder();

        audiorecord = (ToggleButton)findViewById(R.id.audio);
        playButton = (Button)findViewById(R.id.play);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contPlayer%2 != 0){
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(String.valueOf(file));
                        player.prepare();
                        player.start();
                    } catch (IOException e) {
                    }
                }

                if(contPlayer%2 == 0) {
                    player.release();
                }
            }
        });

        audiorecord.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(contRecorder%2 != 0){
                    try {

                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

                        mediaRecorder.setOutputFile(file);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                        mediaRecorder.prepare();
                        mediaRecorder.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else if(contRecorder%2 == 0) {

                    mediaRecorder.stop();
                    mediaRecorder.release();

                }

                contRecorder++;

            }
        });
    }
}
