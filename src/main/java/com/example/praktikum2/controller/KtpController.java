package com.example.praktikum2.controller;

import com.example.praktikum2.dto.KtpDTO;
import com.example.praktikum2.service.KtpService;
import com.example.praktikum2.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ktp")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KtpController {

    private final KtpService ktpService;

    @PostMapping
    public ResponseEntity<ApiResponse<KtpDTO>> createKtp(@RequestBody KtpDTO ktpDTO) {
        try {
            KtpDTO savedKtp = ktpService.save(ktpDTO);
            return ResponseEntity.ok(ApiResponse.success("Success adding KTP data", savedKtp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<KtpDTO>>> getAllKtp() {
        return ResponseEntity.ok(ApiResponse.success("Success fetching KTP data", ktpService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KtpDTO>> getKtpById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Success fetching KTP data", ktpService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KtpDTO>> updateKtp(@PathVariable Integer id, @RequestBody KtpDTO ktpDTO) {
        try {
            KtpDTO updatedKtp = ktpService.update(id, ktpDTO);
            return ResponseEntity.ok(ApiResponse.success("Success updating KTP data", updatedKtp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteKtp(@PathVariable Integer id) {
        try {
            ktpService.delete(id);
            return ResponseEntity.ok(ApiResponse.success("Success deleting KTP data", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
