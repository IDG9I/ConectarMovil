package com.example.conectamvil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ImagenPerfil extends AppCompatActivity {

    Button btnModificar, btnOtraFoto;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView FotoPerfil;
    private Bitmap imagenSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_perfil);

        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnOtraFoto = findViewById(R.id.btnOtraFoto);
        FotoPerfil = findViewById(R.id.FotoPerfil);

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
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);

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
