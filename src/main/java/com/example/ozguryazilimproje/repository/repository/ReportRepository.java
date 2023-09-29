package com.example.ozguryazilimproje.repository.repository;


import com.example.ozguryazilimproje.repository.entity.Report;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Transactional
@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query(value = "select * from report order by create_date DESC  ",
    nativeQuery = true)
    List<Report>  sortByDate();

}
