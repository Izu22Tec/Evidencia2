package com.consultorio;

import com.consultorio.auth.Administrador;
import com.consultorio.auth.AuthService;
import com.consultorio.domain.Cita;
import com.consultorio.domain.Doctor;
import com.consultorio.domain.Paciente;
import com.consultorio.persistence.ArchivoCSV;
import com.consultorio.persistence.Repositorio;
import com.consultorio.ui.Consola;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static final Path DB_DIR = Path.of("db");
    public static final Path ADMINS = DB_DIR.resolve("admins.csv");
    public static final Path DOCTORES = DB_DIR.resolve("doctores.csv");
    public static final Path PACIENTES = DB_DIR.resolve("pacientes.csv");
    public static final Path CITAS = DB_DIR.resolve("citas.csv");

    public static void main(String[] args) {
        try {
            asegurarArchivosDB();
            Repositorio<Administrador> repoAdmins = ArchivoCSV.adminRepo(ADMINS);
            Repositorio<Doctor> repoDoc = ArchivoCSV.doctorRepo(DOCTORES);
            Repositorio<Paciente> repoPac = ArchivoCSV.pacienteRepo(PACIENTES);
            Repositorio<Cita> repoCita = ArchivoCSV.citaRepo(CITAS);
            AuthService auth = new AuthService(repoAdmins);
            Consola ui = new Consola(repoDoc, repoPac, repoCita, auth);
            ui.iniciar();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void asegurarArchivosDB() throws Exception {
        if (!Files.exists(DB_DIR)) Files.createDirectories(DB_DIR);
        if (!Files.exists(ADMINS)) {
            Files.createFile(ADMINS);
            String linea = "admin," + AuthService.sha256("admin123") + System.lineSeparator();
            Files.writeString(ADMINS, linea);
        }
        if (!Files.exists(DOCTORES)) Files.createFile(DOCTORES);
        if (!Files.exists(PACIENTES)) Files.createFile(PACIENTES);
        if (!Files.exists(CITAS)) Files.createFile(CITAS);
    }
}