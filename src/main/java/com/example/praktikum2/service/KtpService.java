package com.example.praktikum2.service;

import com.example.praktikum2.dto.KtpDTO;
import java.util.List;

public interface KtpService {
    KtpDTO save(KtpDTO ktpDTO);
    List<KtpDTO> findAll();
    KtpDTO findById(Integer id);
    KtpDTO update(Integer id, KtpDTO ktpDTO);
    void delete(Integer id);
}
