package com.example.testTechnique.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tpe")
public class Tpe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @OneToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @Column(name="sms")
    private double sms = 100;
}
