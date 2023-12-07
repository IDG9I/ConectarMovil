package com.example.conectamvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Chat extends AppCompatActivity {
    String UsuarioContacto, IDUsuario;
    Button btnSalirContacto;
    TextView txtNContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();

        UsuarioContacto = extras.getString("UsuarioContacto");
        IDUsuario = extras.getString("IDUsuario");

        txtNContacto = findViewById(R.id.txtNContacto);
        btnSalirContacto = (Button) findViewById(R.id.btnSalirContacto);

        txtNContacto.setText(UsuarioContacto);

        btnSalirContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chat.this, Menu.class);
                intent.putExtra("IDUsuario", IDUsuario);
                startActivity(intent);
            }
        });
    }
}