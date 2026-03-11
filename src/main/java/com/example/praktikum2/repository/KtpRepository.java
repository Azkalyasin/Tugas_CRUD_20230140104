package com.example.praktikum2.repository;

import com.example.praktikum2.entity.Ktp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KtpRepository extends JpaRepository<Ktp, Integer> {
    boolean existsByNomorKtp(String nomorKtp);
    Optional<Ktp> findByNomorKtp(String nomorKtp);
}
