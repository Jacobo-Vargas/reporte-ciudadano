package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.utils.emails.VerificationCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationCodeService {

    private final Map<String, VerificationCode> codesMap = new ConcurrentHashMap<>();

    public String generateAndStoreCode(String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000); // Código de 6 dígitos
        VerificationCode verification = new VerificationCode(code, LocalDateTime.now().plusMinutes(5));
        codesMap.put(email, verification);
        return code;
    }

    public boolean isCodeValid(String email, String inputCode) {
        VerificationCode verification = codesMap.get(email);
        if (verification != null && verification.getCode().equals(inputCode)) {
            return LocalDateTime.now().isBefore(verification.getExpiresAt());
        }
        return false;
    }

    public void removeCode(String email) {
        codesMap.remove(email);
    }

    public boolean canResend(String email) {
        VerificationCode verification = codesMap.get(email);
        return verification == null || LocalDateTime.now().isAfter(verification.getExpiresAt());
    }
}