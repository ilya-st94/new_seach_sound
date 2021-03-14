package com.example.newzwuk;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private TextView textView2;
    private TextView textView;
    private TextView textView3;
    private Handler handler = new Handler();
    private MediaRecorder mediaRecorder;
    private int numberseekBar;
    private ImageView imageView;
    private Button knopka;
    private boolean start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 = findViewById(R.id.textView2);
        textView = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
        SeekBar seekBar = findViewById(R.id.seekBar);
        imageView =findViewById(R.id.imageView2);
        imageView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        seekBar.setOnSeekBarChangeListener(this);
        knopka = findViewById(R.id.playzapis);
        textView2.setText("0");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textView2.setText(String.valueOf(seekBar.getProgress()));
        numberseekBar = seekBar.getProgress(); //  число на seekBar
    }

    public void startRecorder(){
        if (mediaRecorder == null)
        {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/dev/null");
            try
            {
                mediaRecorder.prepare();
            }catch (java.io.IOException ioe) {
                ioe.printStackTrace();
            }
            try
            {
                mediaRecorder.start();
            }catch (java.lang.SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public double getargument() throws Exception {
double pologitelnayaperemena = Math.abs(sound2());
        BigDecimal bigDecimal = new BigDecimal(pologitelnayaperemena);
        bigDecimal = bigDecimal.setScale(2, bigDecimal.ROUND_CEILING);
        double number = bigDecimal.doubleValue(); // number - это число округленное до 100-х
        if(Double.isNaN(number)){
            throw(new Exception("NAN"));
        }
        else if(Double.isInfinite(number)){
            throw(new Exception("INFINITE"));
        }
        return number;
    }

    public double sound2(){

        return 20 * Math.log10(getAmplitude()/10);
    }

    public double getAmplitude() {
        if (mediaRecorder != null)
            return  (mediaRecorder.getMaxAmplitude());
        else
            return 0;
    }

    public void onResume()
    {
        super.onResume();
        startRecorder();
    }

    public void onPause()
    {
        super.onPause();
        stopRecorder();
    }

    public void stopRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public void knopkatart(View view) {
        if(!start){
            knopka.setText(R.string.stopname);
            start =true;
            textView3.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (start){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    textView3.setText(getargument()+ " DB ");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (start){
                        try {
                            if(getargument()>numberseekBar && numberseekBar!=0){ // если звуковая аплетуда больше числа seekBar то загарется красная кнопка
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setVisibility(View.VISIBLE);

                                    }
                                });

                                Runnable runnable = new Runnable() {
                                    int i;
                                    @Override
                                    public void run() {

                                        if (++i % 2 == 0) {
                                            textView.setVisibility(View.VISIBLE);
                                        } else {
                                            textView.setVisibility(View.INVISIBLE);
                                        }
                                        handler.postDelayed(this, 700);

                                    }
                                };
                                handler.postDelayed(runnable, 700);

                                Runnable runnable1 = new Runnable() {
                                    int i;
                                    @Override
                                    public void run() {
                                  if(i++%2==0){
                                   imageView.setImageResource(R.drawable.redknopka);
                                  }else{
                                      imageView.setImageResource(R.drawable.redknopka2);
                                  }
                                      handler.postDelayed(this, 1000);
                                    }
                                };
                                handler.postDelayed(runnable1, 1000);

                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                 handler.removeCallbacks(runnable);
                                handler.removeCallbacks(runnable1);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setVisibility(View.INVISIBLE);
                                        textView.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }

                    }

                }
            }).start();
        }else{

            start =false;
            knopka.setText(R.string.startname);
            textView3.setVisibility(View.INVISIBLE);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        start =false;
    }

    public void Knopkaperehod(View view) {
        Intent intent = new Intent(MainActivity.this, Infopanel.class);
        startActivity(intent);
    }
}