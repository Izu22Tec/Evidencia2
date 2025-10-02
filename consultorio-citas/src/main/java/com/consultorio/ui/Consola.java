package com.consultorio.ui;

import com.consultorio.auth.AuthService;
import com.consultorio.domain.*;
import com.consultorio.persistence.Repositorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Consola {
    private final Repositorio<Doctor> repoDoc;
    private final Repositorio<Paciente> repoPac;
    private final Repositorio<Cita> repoCita;
    private final AuthService auth;
    private final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Consola(Repositorio<Doctor> repoDoc, Repositorio<Paciente> repoPac, Repositorio<Cita> repoCita, AuthService auth) {
        this.repoDoc = repoDoc; this.repoPac = repoPac; this.repoCita = repoCita; this.auth = auth;
    }

    public void iniciar() {
        System.out.println("=== Sistema de Citas (Consultorio) ===");
        if (!loginLoop()) return;
        menuPrincipal();
    }

    private boolean loginLoop() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: "); String u = sc.nextLine().trim();
            System.out.print("Contraseña: "); String p = sc.nextLine().trim();
            if (auth.login(u, p)) { System.out.println("Acceso concedido."); return true; }
            System.out.println("Credenciales inválidas. Intentos restantes: " + (2 - i));
        }
        System.out.println("Demasiados intentos. Saliendo.");
        return false;
    }

    private void menuPrincipal() {
        while (true) {
            System.out.println();
            System.out.println("1) Doctores  2) Pacientes  3) Citas  4) Guardar y salir");
            System.out.print("Seleccione opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": menuDoctores(); break;
                case "2": menuPacientes(); break;
                case "3": menuCitas(); break;
                case "4": System.out.println("Hasta pronto!"); return;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    // -------- Doctores --------
    private void menuDoctores() {
        while (true) {
            System.out.println("\n--- Doctores ---");
            System.out.println("1) Alta  2) Listar  3) Editar  4) Eliminar  5) Volver");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": altaDoctor(); break;
                case "2": listarDoctores(); break;
                case "3": editarDoctor(); break;
                case "4": eliminarDoctor(); break;
                case "5": return;
                default: System.out.println("Opción inválida.");
            }
        }
    }
    private void altaDoctor() {
        Doctor d = new Doctor();
        d.setId("DOC-" + UUID.randomUUID().toString().substring(0,8));
        System.out.print("Nombre completo: "); d.setNombre(sc.nextLine().trim());
        System.out.print("Especialidad: "); d.setEspecialidad(sc.nextLine().trim());
        repoDoc.guardar(d);
        System.out.println("Doctor creado con ID: " + d.getId());
    }
    private void listarDoctores() {
        List<Doctor> l = repoDoc.listar();
        if (l.isEmpty()) { System.out.println("No hay doctores."); return; }
        l.forEach(System.out::println);
    }
    private void editarDoctor() {
        System.out.print("ID de doctor: ");
        String id = sc.nextLine().trim();
        Optional<Doctor> od = repoDoc.buscarPorId(id);
        if (od.isEmpty()) { System.out.println("No encontrado."); return; }
        Doctor d = od.get();
        System.out.print("Nuevo nombre (enter mantiene " + d.getNombre() + "): ");
        String nn = sc.nextLine();
        if (!nn.isBlank()) d.setNombre(nn.trim());
        System.out.print("Nueva especialidad (enter mantiene " + d.getEspecialidad() + "): ");
        String ne = sc.nextLine();
        if (!ne.isBlank()) d.setEspecialidad(ne.trim());
        repoDoc.guardar(d);
        System.out.println("Actualizado.");
    }
    private void eliminarDoctor() {
        System.out.print("ID de doctor: ");
        String id = sc.nextLine().trim();
        if (confirmar("¿Eliminar definitivamente?")) {
            repoDoc.eliminar(id);
            System.out.println("Eliminado.");
        }
    }

    // -------- Pacientes --------
    private void menuPacientes() {
        while (true) {
            System.out.println("\n--- Pacientes ---");
            System.out.println("1) Alta  2) Listar  3) Editar  4) Eliminar  5) Volver");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": altaPaciente(); break;
                case "2": listarPacientes(); break;
                case "3": editarPaciente(); break;
                case "4": eliminarPaciente(); break;
                case "5": return;
                default: System.out.println("Opción inválida.");
            }
        }
    }
    private void altaPaciente() {
        Paciente p = new Paciente();
        p.setId("PAC-" + UUID.randomUUID().toString().substring(0,8));
        System.out.print("Nombre completo: "); p.setNombre(sc.nextLine().trim());
        repoPac.guardar(p);
        System.out.println("Paciente creado con ID: " + p.getId());
    }
    private void listarPacientes() {
        List<Paciente> l = repoPac.listar();
        if (l.isEmpty()) { System.out.println("No hay pacientes."); return; }
        l.forEach(System.out::println);
    }
    private void editarPaciente() {
        System.out.print("ID de paciente: ");
        String id = sc.nextLine().trim();
        Optional<Paciente> op = repoPac.buscarPorId(id);
        if (op.isEmpty()) { System.out.println("No encontrado."); return; }
        Paciente p = op.get();
        System.out.print("Nuevo nombre (enter mantiene " + p.getNombre() + "): ");
        String nn = sc.nextLine();
        if (!nn.isBlank()) p.setNombre(nn.trim());
        repoPac.guardar(p);
        System.out.println("Actualizado.");
    }
    private void eliminarPaciente() {
        System.out.print("ID de paciente: ");
        String id = sc.nextLine().trim();
        if (confirmar("¿Eliminar definitivamente?")) {
            repoPac.eliminar(id);
            System.out.println("Eliminado.");
        }
    }

    // -------- Citas --------
    private void menuCitas() {
        while (true) {
            System.out.println("\n--- Citas ---");
            System.out.println("1) Crear  2) Listar  3) Reprogramar  4) Cancelar  5) Volver");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": crearCita(); break;
                case "2": listarCitas(); break;
                case "3": reprogramarCita(); break;
                case "4": cancelarCita(); break;
                case "5": return;
                default: System.out.println("Opción inválida.");
            }
        }
    }
    private void crearCita() {
        Cita c = new Cita();
        c.setId("CIT-" + UUID.randomUUID().toString().substring(0,8));
        System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
        String sdt = sc.nextLine().trim();
        try { c.setFechaHora(LocalDateTime.parse(sdt, FMT)); }
        catch (Exception e) { System.out.println("Formato inválido: " + e.getMessage()); return; }
        System.out.print("Motivo: "); c.setMotivo(sc.nextLine().trim());
        System.out.print("ID Doctor: "); String docId = sc.nextLine().trim();
        if (repoDoc.buscarPorId(docId).isEmpty()) { System.out.println("Doctor no existe."); return; }
        System.out.print("ID Paciente: "); String pacId = sc.nextLine().trim();
        if (repoPac.buscarPorId(pacId).isEmpty()) { System.out.println("Paciente no existe."); return; }

        boolean conflicto = repoCita.listar().stream()
                .anyMatch(x -> x.getDoctorId().equals(docId)
                        && x.getEstado() != EstadoCita.CANCELADA
                        && x.getFechaHora().format(FMT).equals(c.getFechaHora().format(FMT)));
        if (conflicto) { System.out.println("Conflicto de horario para el doctor."); return; }

        c.setDoctorId(docId);
        c.setPacienteId(pacId);
        c.setEstado(EstadoCita.PROGRAMADA);
        repoCita.guardar(c);
        System.out.println("Cita creado con ID: " + c.getId());
    }
    private void listarCitas() {
        List<Cita> l = repoCita.listar();
        if (l.isEmpty()) { System.out.println("No hay citas."); return; }
        l.forEach(System.out::println);
    }
    private void reprogramarCita() {
        System.out.print("ID de cita: ");
        String id = sc.nextLine().trim();
        Optional<Cita> oc = repoCita.buscarPorId(id);
        if (oc.isEmpty()) { System.out.println("No encontrada."); return; }
        Cita c = oc.get();
        System.out.print("Nueva fecha y hora (YYYY-MM-DD HH:MM): ");
        String sdt = sc.nextLine().trim();
        try {
            c.setFechaHora(LocalDateTime.parse(sdt, FMT));
            c.setEstado(EstadoCita.REPROGRAMADA);
            repoCita.guardar(c);
            System.out.println("Cita reprogramada.");
        } catch (Exception e) {
            System.out.println("Formato inválido: " + e.getMessage());
        }
    }
    private void cancelarCita() {
        System.out.print("ID de cita: ");
        String id = sc.nextLine().trim();
        Optional<Cita> oc = repoCita.buscarPorId(id);
        if (oc.isEmpty()) { System.out.println("No encontrada."); return; }
        Cita c = oc.get();
        if (confirmar("¿Confirmas cancelar?")) {
            c.setEstado(EstadoCita.CANCELADA);
            repoCita.guardar(c);
            System.out.println("Cita cancelada.");
        }
    }

    private boolean confirmar(String msg) {
        System.out.print(msg + " (s/n): ");
        String r = sc.nextLine().trim().toLowerCase();
        return r.equals("s");
    }
}