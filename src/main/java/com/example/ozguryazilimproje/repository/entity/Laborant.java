package com.example.ozguryazilimproje.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Name;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Laborant")
public class Laborant {
    @Id
    @Column(name = "LaborantId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laborantId;
    @Column(name="laborantName" , nullable = false)
    private String laborantName;
    @Column(name = "laborantSurName" ,nullable = false)
    private String laborantSurName;
    @Column(name = "password")
    private String password;
    @Column(name = "userName")
    private String userName;
    @Valid
    @Column(name = "hospitalIdNo",unique = true,nullable = false  ,length = 7)
    @Size(max = 7,min = 7,message = "7 karakter olmalı")
    private String hospitalIdNo;
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;


}
