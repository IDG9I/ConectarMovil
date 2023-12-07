package com.example.conectamvil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conectamvil.Modelo.Usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ImagenPerfil extends AppCompatActivity {

    Button btnModificar, btnOtraFoto;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView FotoPerfil;
    TextView txtUsuarioNombePerfil;
    String IDUsuario;
    private Bitmap imagenSeleccionada;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_perfil);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        Bundle extras = getIntent().getExtras();

        IDUsuario = extras.getString("NombreUsuario");

        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnOtraFoto = findViewById(R.id.btnOtraFoto);
        FotoPerfil = findViewById(R.id.FotoPerfil);
        txtUsuarioNombePerfil = findViewById(R.id.txtUsuarioNombePerfil);
        buscarLuchador();

        btnOtraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImagenPerfil.this, Menu.class);
                startActivity(intent);
            }
        });
        Log.e("Nombre INICIO", "NOMBRE: " + IDUsuario);
    }

    private void buscarLuchador() {
        Log.v("Nombre INICIO", "NOMBRE: " + IDUsuario);

        //este si o si porque busca de la base de dato (Usuario) al otro apartado (Usuario1)
        DatabaseReference dbUsuarios = databaseReference.child("Usuarios1");

        //das el dato IDUsuario para buscar la id del usuario
        dbUsuarios.child(IDUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuarios users = dataSnapshot.getValue(Usuarios.class);

                    if (users != null && users.getNombreUser() != null) {
                        txtUsuarioNombePerfil.setText(users.getNombreUser());
                        Log.v("Nombre", "NOMBRE: " + users.getNombreUser());
                    } else {
                        Log.e("ERROR DG9", "El nombre de usuario es nulo");
                    }
                } else {
                    Toast.makeText(ImagenPerfil.this, "ERROR DE USUARIO", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR DG9", "NO EXISTE EL USUARIO: " + IDUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de datos
                String errorMessage = "ERROR DE USUARIO EN BASE DE DATOS: " + databaseError.getMessage();
                Toast.makeText(ImagenPerfil.this, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("ERROR DG9", errorMessage);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Obtiene la Uri de la imagen seleccionada
            Uri selectedImageUri = data.getData();

            try {
                // Convierte la Uri a Bitmap
                imagenSeleccionada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                // Muestra la imagen en el ImageView
                FotoPerfil.setImageBitmap(getRoundedBitmap(imagenSeleccionada));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        // Escalar la imagen a un tamaño fijo (200x200)
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

        // Crear un Bitmap cuadrado
        Bitmap squareBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

        // Crear un Bitmap circular
        Bitmap circularBitmap = Bitmap.createBitmap(squareBitmap.getWidth(), squareBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(circularBitmap);
        android.graphics.Paint paint = new android.graphics.Paint();
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, squareBitmap.getWidth(), squareBitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle(squareBitmap.getWidth() / 2f, squareBitmap.getHeight() / 2f, squareBitmap.getWidth() / 2f, paint);
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(squareBitmap, rect, rect, paint);

        return circularBitmap;
    }
}
