package com.consultorio.auth;

import java.util.Objects;

public class Administrador {
    private String usuario;
    private String passwordHash; // SHA-256 hex

    public Administrador() {}
    public Administrador(String usuario, String passwordHash) {
        this.usuario = usuario; this.passwordHash = passwordHash;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String toCSV() { return usuario + "," + passwordHash; }
    public static Administrador fromCSV(String line) {
        String[] p = line.split(",", -1);
        if (p.length < 2) return null;
        return new Administrador(p[0].trim(), p[1].trim());
    }

    @Override public boolean equals(Object o) { return o instanceof Administrador && usuario.equals(((Administrador)o).usuario); }
    @Override public int hashCode() { return Objects.hash(usuario); }
}