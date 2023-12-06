package com.example.conectamvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrarUser extends AppCompatActivity {

    Button btnRegistrarUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_user);

        btnRegistrarUser = (Button)findViewById(R.id.btnRegistrarUser);

        btnRegistrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrarUser.this, ImagenPerfil.class);
                startActivity(intent);
            }
        });
    }


}