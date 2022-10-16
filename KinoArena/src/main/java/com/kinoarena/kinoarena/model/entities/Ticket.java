package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column
//    private TicketType ticketType;
//    @Column
//    private Projection projection;
//    @Column
//    private User user;
//    @Column
//    private Seat seat;
}
