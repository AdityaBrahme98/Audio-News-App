package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class AudioPlayer extends AppCompatActivity {

    Button start,back;
    TextView name;
    SeekBar sb;
    Thread upd_seekbar;
    MediaPlayer mediaPlayer;
    int val;
    String aName, aLink;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        mProgress = new ProgressDialog(AudioPlayer.this);
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();

        start = findViewById(R.id.pause);
        back = findViewById(R.id.back);
        name = findViewById(R.id.textView15);
        sb = findViewById(R.id.seekBar);

        Intent i = getIntent();
        Bundle bundle =i.getExtras();
        aName = bundle.getString("name");
        aLink= bundle.getString("link");
        val = bundle.getInt("key");

        name.setText(aName);

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(aLink);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        upd_seekbar = new Thread(){

            @Override
            public void run()
            {
                int t_dur = mediaPlayer.getDuration();
                int cur_pos = 0;

                while(cur_pos <t_dur)
                {
                    try {

                        sleep(500);
                        cur_pos = mediaPlayer.getCurrentPosition();

                        sb.setProgress(cur_pos);

                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                cur_pos = 0;
                sb.setProgress(cur_pos);
                mediaPlayer.stop();
                super.run();
                return_to(val);
            }
        };
        mProgress.dismiss();
        mediaPlayer.start();
        upd_seekbar.start();

        sb.setMax(mediaPlayer.getDuration());
        sb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN );

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()){
                    start.setText("Play");
                    mediaPlayer.pause();
                }
                else
                {
                    start.setText("Pause");
                    mediaPlayer.start();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){

                    start.setText("Play");
                    mediaPlayer.pause();

                }

                return_to(val);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(mediaPlayer.isPlaying()){

            start.setText("Play");
            mediaPlayer.pause();

        }

        return_to(val);
    }

    public void return_to(int val){
        if(val == 1)
        {
            Intent intent = new Intent(AudioPlayer.this,ListOfNews.class);
            startActivity(intent);
            finish();
        }
        else if(val == 2)
        {
            Intent intent = new Intent(AudioPlayer.this,ListOfArt.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(AudioPlayer.this,ListOfBooks.class);
            startActivity(intent);
            finish();
        }
    }
}
