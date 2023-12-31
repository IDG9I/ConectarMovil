package com.example.conectamvil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.conectamvil.Modelo.Usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Agregar extends AppCompatActivity {

    private EditText editTextNombreUsuario;
    private Button btnAgregarUsuario;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> userList;
    String SeLContacto;
    ListView listView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        DatabaseReference dbUsuarios = databaseReference.child("Usuarios1");

        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);

        listView = findViewById(R.id.ListVview);
        userList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);

        dbUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpiar la lista antes de agregar los nuevos usuarios
                userList.clear();

                // Iterar sobre los nodos hijos (usuarios) en la base de datos
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Obtener el nombre del usuario y agregarlo a la lista
                    String userName = userSnapshot.child("nombreUser").getValue(String.class);
                    userList.add(userName);
                }

                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores en la lectura de datos
            }
        });

        //cuando le das click a un nombre de la lista se muestra en el edittext
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                SeLContacto = userList.get(position);
                editTextNombreUsuario.setText(SeLContacto);

            }
        });

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = editTextNombreUsuario.getText().toString().trim();

                if (!nombreUsuario.isEmpty()) {
                    AgregarContacto();

                }
            }
        });
    }

    private void AgregarContacto() {

        String Usuario2 = editTextNombreUsuario.getText().toString();

        //este si o si porque busca de la base de dato (Usuario) al otro apartado (Usuario1)
        DatabaseReference dbUsuarios = databaseReference.child("Usuarios1");

        //das el dato IDUsuario para buscar la id del usuario
        dbUsuarios.child(Usuario2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuarios user = snapshot.getValue(Usuarios.class);

                    if (user != null && user.getNombreUser() != null) {

                        Intent intent = new Intent();
                        intent.putExtra("NOMBRE_USUARIO", Usuario2);
                        setResult(RESULT_OK, intent);
                        finish();

                    } else {
                        Toast.makeText(Agregar.this, "Escriba El Usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Agregar.this, "ERROR NO ENCONTRADO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String errorMessage = "ERROR DE USUARIO EN BASE DE DATOS: " + error.getMessage();
            }
        });
    }
}