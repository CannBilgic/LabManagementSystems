package com.example.ozguryazilimproje.repository.repository;

import com.example.ozguryazilimproje.repository.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query(value = "select p from Patient p where p.name = :name or p.surName =:surName or p.TC =:TC ")
    List<Patient> getByNameAndSurname(String name, String surName , Long TC);
    @Query(value = "select p from Patient p where p.userName = :userName and p.password =:password  ")
    Patient Login(String userName, String password);


}
