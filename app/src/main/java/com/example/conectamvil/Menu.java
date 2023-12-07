package com.example.conectamvil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conectamvil.Modelo.Contactos;
import com.example.conectamvil.Modelo.Usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    Button btnAgregarUser, btnVerUsuario;
    TextView ListaUsuario;
    ListView listViewUsuarios;
    String IDUsuario;
    DatabaseReference databaseReference;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> userList;
    private static final int REQUEST_CODE_AGREGAR_USUARIO = 1;

    String UsuarioContacto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        listView = findViewById(R.id.listView);
        userList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);

        /*
        String imagenBase64 = getIntent().getStringExtra("IMAGEN_PERFIL");

        if (imagenBase64 != null && !imagenBase64.isEmpty()) {
            // Convierte la cadena Base64 de nuevo a un Bitmap
            Bitmap imagenPerfil = convertirBase64AImagen(imagenBase64);

            // Muestra la imagen en un ImageView en tu layout de actividad_menu.xml
            ImageView imageViewPerfil = findViewById(R.id.imageView222);
            imageViewPerfil.setImageBitmap(imagenPerfil);
        }

        */

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        DatabaseReference dbUsuarios = databaseReference.child("Usuarios1");


        btnAgregarUser =(Button)findViewById(R.id.btnAgregarUser);
        listViewUsuarios = findViewById(R.id.listView);
        btnVerUsuario = (Button)findViewById(R.id.btnVerUsuario);

        Bundle extras = getIntent().getExtras();
        IDUsuario = extras.getString("IDUsuario");

        /*
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
        */

        btnAgregarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Agregar.class);
                startActivityForResult(intent, REQUEST_CODE_AGREGAR_USUARIO);
            }
        });

        btnVerUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ImagenPerfil.class);
                intent.putExtra("NombreUsuario", IDUsuario);
                startActivity(intent);
            }
        });


        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Menu.this, "Conectando Con: "+ userList.get(position), Toast.LENGTH_LONG).show();

                UsuarioContacto = userList.get(position);
                Intent intent = new Intent(Menu.this, Chat.class);
                intent.putExtra("UsuarioContacto", UsuarioContacto);
                intent.putExtra("IDUsuario", IDUsuario);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AGREGAR_USUARIO && resultCode == RESULT_OK && data != null) {
            // Obtiene el nuevo usuario de AgregarUsuarioActivity
            String nuevoUsuario = data.getStringExtra("NOMBRE_USUARIO");

            // Agrega el nuevo usuario a la lista
            userList.add(nuevoUsuario);

            // Notifica al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged();
            //GuardarContactos();
        }
    }

    /*
    private void GuardarContactos() {



        //obtener la tabla
        DatabaseReference dbUsuarios = databaseReference.child("Contacto");

        //crear Usuario
        Contactos contactos = new Contactos(IDUsuario, userList);

        // Guardar el Usuario en la base de datos de Firebase
        dbUsuarios.child(IDUsuario).setValue(contactos);

        userList.add(contactos.getContactos().toString());

        // Mostrar un mensaje de éxito
        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

    }

    */
    // Función para convertir una imagen a cadena Base64
    private String convertirImagenABase64(Bitmap imagen) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImagen = baos.toByteArray();
        return Base64.encodeToString(byteArrayImagen, Base64.DEFAULT);
    }

    // Función para convertir una cadena Base64 a un Bitmap
    private Bitmap convertirBase64AImagen(String base64) {
        byte[] byteArray = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


}
