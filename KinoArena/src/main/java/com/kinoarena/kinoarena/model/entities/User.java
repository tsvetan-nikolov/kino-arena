package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String middleName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private String address;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @Column
    private boolean isAdmin;
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
}
