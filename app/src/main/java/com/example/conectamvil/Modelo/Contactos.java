package com.example.conectamvil.Modelo;

import java.util.ArrayList;

public class Contactos {
    String Usuario;
    public ArrayList Contactos;

    public Contactos() {
    }

    public Contactos(String usuario, ArrayList contactos) {
        Usuario = usuario;
        Contactos = contactos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public ArrayList getContactos() {
        return Contactos;
    }

    public void setContactos(ArrayList contactos) {
        Contactos = contactos;
    }
}
