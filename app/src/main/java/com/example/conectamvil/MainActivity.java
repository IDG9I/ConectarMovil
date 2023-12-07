package com.example.conectamvil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.Query;

import com.example.conectamvil.Modelo.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    Button btnIniciar, btnCrear;
    EditText txtContraseñaL, txtUsuarioL;
    DatabaseReference databaseReference;
    String IDUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        txtUsuarioL = findViewById(R.id.txtUsuarioL);
        txtContraseñaL= findViewById(R.id.txtContraseñaL);

        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        btnCrear = (Button)findViewById(R.id.btnCrear);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarUsuario();

            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrarUser.class);
                startActivity(intent);
            }
        });

    }



    private void buscarUsuario() {

        String Usuarioo = txtUsuarioL.getText().toString();
        String Contraseña = txtContraseñaL.getText().toString();

        //este si o si porque busca de la base de dato (Usuario) al otro apartado (Usuario1)
        DatabaseReference dbUsuarios = databaseReference.child("Usuarios1");

        //das el dato IDUsuario para buscar la id del usuario
        dbUsuarios.child(Usuarioo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuarios user = snapshot.getValue(Usuarios.class);

                    if (user != null && user.getNombreUser() != null) {

                        String contraseña = user.getContraseña();

                        if(Contraseña.equals(contraseña)){

                            Intent inten = new Intent(MainActivity.this, Menu.class);
                            inten.putExtra("IDUsuario", Usuarioo);
                            startActivity(inten);

                            Toast.makeText(MainActivity.this, "Sesion Iniciada", Toast.LENGTH_SHORT).show();

                        }else{
                            txtContraseñaL.setText("");
                            Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                            Log.e("VERIFICANDO DG9", contraseña);
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Escriba su Usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "ERROR DE USUARIO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String errorMessage = "ERROR DE USUARIO EN BASE DE DATOS: " + error.getMessage();
            }
        });
    }
}