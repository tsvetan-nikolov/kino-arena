package com.kinoarena.kinoarena.model.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    @Transient
    public String getAuthority() {
        return this.name;
    }
}
