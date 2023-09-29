package com.example.ozguryazilimproje.repository.repository;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.repository.entity.Laborant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface LaborantRepository extends JpaRepository<Laborant,Long> {
    @Query(value = "select l from Laborant l where l.laborantName = :laborantName or l.laborantSurName =:laborantSurName  ")
    List<Laborant> getByNameAndSurname(String laborantName, String laborantSurName);
    @Query(value = "select l from Laborant l where l.userName = :userName and l.password =:password  ")
    Laborant Login(String userName, String password);
}
