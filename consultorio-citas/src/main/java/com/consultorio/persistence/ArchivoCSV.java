package com.consultorio.persistence;

import com.consultorio.auth.Administrador;
import com.consultorio.domain.Cita;
import com.consultorio.domain.Doctor;
import com.consultorio.domain.Paciente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArchivoCSV<T> implements Repositorio<T> {
    private final Path ruta;
    private final Function<String, T> parser;
    private final Function<T, String> serializer;
    private final Function<T, String> idExtractor;

    private Map<String, T> cache = new LinkedHashMap<>();

    public ArchivoCSV(Path ruta, Function<String, T> parser, Function<T, String> serializer, Function<T, String> idExtractor) {
        this.ruta = ruta;
        this.parser = parser;
        this.serializer = serializer;
        this.idExtractor = idExtractor;
        recargar();
    }

    private void recargar() {
        cache.clear();
        try {
            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta.getParent());
                Files.createFile(ruta);
            }
            for (String ln : Files.readAllLines(ruta)) {
                if (ln == null || ln.isBlank()) continue;
                T e = parser.apply(ln);
                if (e == null) continue;
                cache.put(idExtractor.apply(e), e);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo " + ruta + ": " + e.getMessage());
        }
    }

    @Override public List<T> listar() { return new ArrayList<>(cache.values()); }
    @Override public Optional<T> buscarPorId(String id) { return Optional.ofNullable(cache.get(id)); }
    @Override public void guardar(T e) { cache.put(idExtractor.apply(e), e); flush(); }
    @Override public void eliminar(String id) { cache.remove(id); flush(); }

    private void flush() {
        try {
            String data = cache.values().stream().map(serializer).collect(Collectors.joining(System.lineSeparator()));
            Files.writeString(ruta, data + (data.isEmpty() ? "" : System.lineSeparator()));
        } catch (IOException e) {
            System.out.println("Error escribiendo " + ruta + ": " + e.getMessage());
        }
    }

    // Fábricas
    public static Repositorio<Administrador> adminRepo(Path p) {
        return new ArchivoCSV<>(p, Administrador::fromCSV, Administrador::toCSV, Administrador::getUsuario);
    }
    public static Repositorio<Doctor> doctorRepo(Path p) {
        return new ArchivoCSV<>(p, Doctor::fromCSV, Doctor::toCSV, Doctor::getId);
    }
    public static Repositorio<Paciente> pacienteRepo(Path p) {
        return new ArchivoCSV<>(p, Paciente::fromCSV, Paciente::toCSV, Paciente::getId);
    }
    public static Repositorio<Cita> citaRepo(Path p) {
        return new ArchivoCSV<>(p, Cita::fromCSV, Cita::toCSV, Cita::getId);
    }
}