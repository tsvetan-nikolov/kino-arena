package com.kinoarena.kinoarena.model.entities;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "invalid_tokens")
@Builder
public class InvalidToken {
    @Id
    @Column
    private String token;

    @Column
    private LocalDateTime expiration;

}
