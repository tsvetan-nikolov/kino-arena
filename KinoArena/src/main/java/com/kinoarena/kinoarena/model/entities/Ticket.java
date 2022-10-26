package com.kinoarena.kinoarena.model.entities;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;
    @ManyToOne
    @JoinColumn(name = "projection_id")
    private Projection projection;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
