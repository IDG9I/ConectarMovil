package com.example.conectamvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conectamvil.Modelo.Usuarios;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarUser extends AppCompatActivity {

    Button btnRegistrarUser;
    EditText txtCreacionUsuario, txtCorreo, txtTelefono, txtContraseñaR, txtContraseñaConfirR;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_user);


        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");


        btnRegistrarUser = (Button)findViewById(R.id.btnRegistrarUser);

        txtCreacionUsuario = findViewById(R.id.txtCreacionUsuario);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtContraseñaR = findViewById(R.id.txtContraseñaR);
        txtContraseñaConfirR = findViewById(R.id.txtContraseñaConfirR);

        btnRegistrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuarios();
            }
        });
    }

    private void registrarUsuarios() {
        String NombreUser = txtCreacionUsuario.getText().toString().trim();
        String Correo = txtCorreo.getText().toString().trim();
        String Telefono = txtTelefono.getText().toString().trim();
        String Contraseña = txtContraseñaR.getText().toString().trim();



        // Verificar Valores Validos
        if (!NombreUser.isEmpty() && !Correo.isEmpty() && !Telefono.isEmpty() && !Contraseña.isEmpty()) {
            //obtener la tabla
            DatabaseReference dbUsuarios = databaseReference.child("Usuarios1");



            //nuevo ID
            String nuevoID = dbUsuarios.push().getKey();

            //crear Usuario
            Usuarios usuarios = new Usuarios(nuevoID, NombreUser, Correo, Telefono, Contraseña);

            // Guardar el Usuario en la base de datos de Firebase
            dbUsuarios.child(NombreUser).setValue(usuarios);

            txtCreacionUsuario.setText("");
            txtCorreo.setText("");
            txtTelefono.setText("");
            txtContraseñaR.setText("");


            //ir a la creacion de perfil
            Intent intent = new Intent(RegistrarUser.this, ImagenPerfil.class);
            intent.putExtra("NombreUsuario", NombreUser);
            Log.i("Nombre PRO", NombreUser);
            startActivity(intent);

            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar un mensaje de error si los campos están vacíos
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }




}