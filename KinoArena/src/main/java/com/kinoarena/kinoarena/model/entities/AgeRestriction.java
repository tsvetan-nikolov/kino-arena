package com.kinoarena.kinoarena.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "age_restrictions")
@AllArgsConstructor
public class AgeRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String category;
    @Column
    private int age;

//    @OneToMany(mappedBy = "ageRestriction")
//    private List<Movie> movies;
}
