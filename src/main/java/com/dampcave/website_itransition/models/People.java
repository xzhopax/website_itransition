package com.dampcave.website_itransition.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "peoples")
@Data
//@NoArgsConstructor
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration")
    private Date registration;

    @Column(name = "lastlogin")
    private Date lastLogin;

    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private User user;

    public People() {
        this.registration = new Date();
        this.status = "Unblocked";
    }


    public People(String name, String email) {
        this.name = name;
        this.email = email;
        this.registration = new Date();
        this.status = "Unblocked";
    }


}
