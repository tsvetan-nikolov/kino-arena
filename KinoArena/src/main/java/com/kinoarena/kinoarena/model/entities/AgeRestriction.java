package com.kinoarena.kinoarena.model.entities;

import lombok.*;

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
