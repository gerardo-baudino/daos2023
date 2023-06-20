package com.tsti.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {
    @Id
    private Long dni;
    private String apellido;
    private String nombre;
    private String email;
}
