package com.ironfit.backendmongo.usuario;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioDoc, String> {
    Optional<UsuarioDoc> findByEmail(String email);
    boolean existsByEmail(String email);
}