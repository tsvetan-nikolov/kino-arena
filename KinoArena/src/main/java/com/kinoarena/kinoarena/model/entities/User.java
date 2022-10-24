package com.kinoarena.kinoarena.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
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
    @ManyToMany
    @JoinTable(name = "users_favourite_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> favouriteMovies;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = isAdmin ? "Admin" : "User";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singletonList(authority);
    }

    @Override
    @Transient
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        //TODO maybe
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
        //TODO maybe
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        //TODO maybe
    }

    @Override
    public boolean isEnabled() {
        return false;
        //TODO maybe
    }
    //todo add gender table to db and user class

}
