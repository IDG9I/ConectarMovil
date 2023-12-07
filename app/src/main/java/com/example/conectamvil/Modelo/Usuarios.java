package com.example.conectamvil.Modelo;

import com.example.conectamvil.Menu;

import java.util.ArrayList;

public class Usuarios {


    private String ID;
    private String NombreUser;
    private String Correo;
    private String Telefono;
    private String Contraseña;

    public Usuarios() {
    }



    public Usuarios(String ID, String nombreUser, String correo, String telefono, String contraseña) {
        this.ID = ID;
        NombreUser = nombreUser;
        Correo = correo;
        Telefono = telefono;
        Contraseña = contraseña;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombreUser() {
        return NombreUser;
    }

    public void setNombreUser(String nombreUser) {
        NombreUser = nombreUser;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }
}
