package com.example.conectamvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = (Button)findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(MainActivity.this, Menu.class);
                startActivity(inten);
            }
        });
    }
}