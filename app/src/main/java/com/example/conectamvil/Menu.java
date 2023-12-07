package com.example.conectamvil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conectamvil.Modelo.Usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    Button btnAgregarUser;
    TextView ListaUsuario;
    ListView listViewUsuarios;

    DatabaseReference databaseReference;

    public class UsuariosAdapter extends ArrayAdapter<Usuarios> {

        public UsuariosAdapter(Context context, List<Usuarios> usuariosList) {
            super(context, 0, usuariosList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");


        btnAgregarUser =(Button)findViewById(R.id.btnAgregarUser);
        listViewUsuarios = findViewById(R.id.listViewUsuarios);

        btnAgregarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Agregar.class);
                startActivity(intent);
            }
        });
    }


}
