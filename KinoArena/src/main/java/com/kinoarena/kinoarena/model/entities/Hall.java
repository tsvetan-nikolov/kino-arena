package com.kinoarena.kinoarena.model.entities;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "halls")
@Builder
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    @Column
    private int number;
//    @OneToMany(mappedBy = "hall")
//    private Set<Projection> projections = new HashSet<>();
}
