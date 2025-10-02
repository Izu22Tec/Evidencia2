package com.consultorio.persistence;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {
    List<T> listar();
    Optional<T> buscarPorId(String id);
    void guardar(T e);
    void eliminar(String id);
}