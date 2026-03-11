package com.example.praktikum2.mapper;

import com.example.praktikum2.dto.KtpDTO;
import com.example.praktikum2.entity.Ktp;
import org.springframework.stereotype.Component;

@Component
public class KtpMapper {

    public KtpDTO toDto(Ktp ktp) {
        if (ktp == null) return null;
        return new KtpDTO(
                ktp.getId(),
                ktp.getNomorKtp(),
                ktp.getNamaLengkap(),
                ktp.getAlamat(),
                ktp.getTanggalLahir(),
                ktp.getJenisKelamin()
        );
    }

    public Ktp toEntity(KtpDTO dto) {
        if (dto == null) return null;
        Ktp ktp = new Ktp();
        ktp.setId(dto.getId());
        ktp.setNomorKtp(dto.getNomorKtp());
        ktp.setNamaLengkap(dto.getNamaLengkap());
        ktp.setAlamat(dto.getAlamat());
        ktp.setTanggalLahir(dto.getTanggalLahir());
        ktp.setJenisKelamin(dto.getJenisKelamin());
        return ktp;
    }
}
