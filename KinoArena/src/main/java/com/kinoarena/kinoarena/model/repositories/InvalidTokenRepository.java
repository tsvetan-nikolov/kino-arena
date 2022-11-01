package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {


    Optional<InvalidToken> findByToken(String s);
}
