package com.kinoarena.kinoarena.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    public City(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
//    @OneToMany(mappedBy = "city")
//    private Set<Cinema> cinemas = new HashSet<>();
    @OneToMany(mappedBy = "city")
    private Set<User> users = new HashSet<>();
}