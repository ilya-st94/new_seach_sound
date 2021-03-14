package com.example.newzwuk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Infopanel extends AppCompatActivity {
private TextView textView1;
private TextView textView2;
private TextView textView3;
private TextView textView4;
private ImageView imageView1;
private ImageView imageView2;
private ImageView imageView3;
private ImageView imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infopanel);
        textView1 = findViewById(R.id.text4);
        textView2 = findViewById(R.id.textView5);
        textView3 = findViewById(R.id.textView6);
        textView4 = findViewById(R.id.textView7);
        imageView1 = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView3);
        imageView3 = findViewById(R.id.imageView4);
        imageView4 = findViewById(R.id.imageView5);


    }
}