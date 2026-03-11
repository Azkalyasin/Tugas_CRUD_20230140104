package com.example.praktikum2.service.impl;

import com.example.praktikum2.dto.KtpDTO;
import com.example.praktikum2.entity.Ktp;
import com.example.praktikum2.mapper.KtpMapper;
import com.example.praktikum2.repository.KtpRepository;
import com.example.praktikum2.service.KtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KtpServiceImpl implements KtpService {

    private final KtpRepository ktpRepository;
    private final KtpMapper ktpMapper;

    @Override
    public KtpDTO save(KtpDTO ktpDTO) {
        if (ktpRepository.existsByNomorKtp(ktpDTO.getNomorKtp())) {
            throw new RuntimeException("Nomor KTP already exists");
        }
        Ktp ktp = ktpMapper.toEntity(ktpDTO);
        return ktpMapper.toDto(ktpRepository.save(ktp));
    }

    @Override
    public List<KtpDTO> findAll() {
        return ktpRepository.findAll().stream()
                .map(ktpMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public KtpDTO findById(Integer id) {
        return ktpRepository.findById(id)
                .map(ktpMapper::toDto)
                .orElseThrow(() -> new RuntimeException("KTP not found with id: " + id));
    }

    @Override
    public KtpDTO update(Integer id, KtpDTO ktpDTO) {
        Ktp existingKtp = ktpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KTP not found with id: " + id));

        // Check if new nomorKtp already exists in another record
        if (!existingKtp.getNomorKtp().equals(ktpDTO.getNomorKtp()) &&
                ktpRepository.existsByNomorKtp(ktpDTO.getNomorKtp())) {
            throw new RuntimeException("Nomor KTP already exists");
        }

        existingKtp.setNomorKtp(ktpDTO.getNomorKtp());
        existingKtp.setNamaLengkap(ktpDTO.getNamaLengkap());
        existingKtp.setAlamat(ktpDTO.getAlamat());
        existingKtp.setTanggalLahir(ktpDTO.getTanggalLahir());
        existingKtp.setJenisKelamin(ktpDTO.getJenisKelamin());

        return ktpMapper.toDto(ktpRepository.save(existingKtp));
    }

    @Override
    public void delete(Integer id) {
        if (!ktpRepository.existsById(id)) {
            throw new RuntimeException("KTP not found with id: " + id);
        }
        ktpRepository.deleteById(id);
    }
}
