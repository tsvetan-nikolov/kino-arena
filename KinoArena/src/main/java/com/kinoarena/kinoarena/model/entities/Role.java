package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 1;

    @Column
    private String role;
}
