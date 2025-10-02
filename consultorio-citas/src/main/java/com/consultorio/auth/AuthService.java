package com.consultorio.auth;

import com.consultorio.persistence.Repositorio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class AuthService {
    private final Repositorio<Administrador> repoAdmins;

    public AuthService(Repositorio<Administrador> repoAdmins) {
        this.repoAdmins = repoAdmins;
    }

    public boolean login(String usuario, String passwordPlano) {
        String h = sha256(passwordPlano);
        return repoAdmins.listar().stream()
                .anyMatch(a -> a.getUsuario().equals(usuario) && a.getPasswordHash().equals(h));
    }

    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dig = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(dig); // Java 11: convertimos a hex manualmente
        } catch (Exception e) {
            throw new RuntimeException("No se pudo calcular SHA-256", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] HEX = "0123456789abcdef".toCharArray();
        char[] out = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            out[i * 2]     = HEX[v >>> 4];
            out[i * 2 + 1] = HEX[v & 0x0F];
        }
        return new String(out);
    }
}
