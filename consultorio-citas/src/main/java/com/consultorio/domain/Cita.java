package com.consultorio.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cita {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String doctorId;
    private String pacienteId;
    private EstadoCita estado;

    public Cita() {}
    public Cita(String id, LocalDateTime fechaHora, String motivo, String doctorId, String pacienteId, EstadoCita estado) {
        this.id = id; this.fechaHora = fechaHora; this.motivo = motivo; this.doctorId = doctorId; this.pacienteId = pacienteId; this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public String toCSV() {
        return String.join(",", id, FMT.format(fechaHora), motivo, doctorId, pacienteId, estado.name());
    }
    public static Cita fromCSV(String line) {
        String[] p = line.split(",", -1);
        if (p.length < 6) return null;
        return new Cita(
                p[0].trim(),
                LocalDateTime.parse(p[1].trim(), FMT),
                p[2].trim(),
                p[3].trim(),
                p[4].trim(),
                EstadoCita.valueOf(p[5].trim())
        );
    }

    @Override public String toString() {
        return id + " | " + FMT.format(fechaHora) + " | " + motivo + " | Doc:" + doctorId + " | Pac:" + pacienteId + " | " + estado.name();
    }
    @Override public boolean equals(Object o) { return o instanceof Cita && Objects.equals(id, ((Cita)o).id); }
    @Override public int hashCode() { return Objects.hash(id); }
}
