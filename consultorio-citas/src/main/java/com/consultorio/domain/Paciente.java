package com.consultorio.domain;

import java.util.Objects;

public class Paciente {
    private String id;
    private String nombre;

    public Paciente() {}
    public Paciente(String id, String nombre) { this.id = id; this.nombre = nombre; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String toCSV() { return String.join(",", id, nombre); }
    public static Paciente fromCSV(String line) {
        String[] p = line.split(",", -1);
        if (p.length < 2) return null;
        return new Paciente(p[0].trim(), p[1].trim());
    }

    @Override public String toString() { return id + " | " + nombre; }
    @Override public boolean equals(Object o) { return o instanceof Paciente && Objects.equals(id, ((Paciente)o).id); }
    @Override public int hashCode() { return Objects.hash(id); }
}