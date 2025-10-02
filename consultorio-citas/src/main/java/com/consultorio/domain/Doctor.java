package com.consultorio.domain;

import java.util.Objects;

public class Doctor {
    private String id;
    private String nombre;
    private String especialidad;

    public Doctor() {}
    public Doctor(String id, String nombre, String especialidad) {
        this.id = id; this.nombre = nombre; this.especialidad = especialidad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String toCSV() { return String.join(",", id, nombre, especialidad); }
    public static Doctor fromCSV(String line) {
        String[] p = line.split(",", -1);
        if (p.length < 3) return null;
        return new Doctor(p[0].trim(), p[1].trim(), p[2].trim());
    }

    @Override public String toString() { return id + " | " + nombre + " | " + especialidad; }
    @Override public boolean equals(Object o) { return o instanceof Doctor && Objects.equals(id, ((Doctor)o).id); }
    @Override public int hashCode() { return Objects.hash(id); }
}
