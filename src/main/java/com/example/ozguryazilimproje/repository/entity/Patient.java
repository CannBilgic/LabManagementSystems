package com.example.ozguryazilimproje.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "patientId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(name = "TC",nullable = false,unique = true)
    private Long TC;
    @Column(name = "name")
    private String name;
    @Column(name = "surName")
    private String surName;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;


    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
}
