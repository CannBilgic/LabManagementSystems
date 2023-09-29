package com.example.ozguryazilimproje.repository.repository;

import com.example.ozguryazilimproje.repository.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface ImageRepository extends JpaRepository<Image,Long> {


}
