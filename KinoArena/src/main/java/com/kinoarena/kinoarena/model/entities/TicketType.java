package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ticket_types")
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String type;
    @Column
    private double additionalCost;
}
