package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.model.entities.CodeValidation;
import com.uniquindio.reporte.repository.CodeValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CodeValidationServiceIpml {
    @Autowired
    private CodeValidationRepository codeValidationRepository;


    public CodeValidation saveCode(String code, String email){
        CodeValidation codeValidation= new CodeValidation();
        if(code!=null && !code.isEmpty()) {
            codeValidation.setCode(code);
            if (email != null || !email.isEmpty()) {
                codeValidation.setEmailUser(email);
                codeValidation.setExpiresAt(LocalDateTime.now().plusMinutes(15));
                return codeValidationRepository.save(codeValidation);
            }
        }
        return null;
    }

    public Optional<CodeValidation> getCodeValidation(String code) {
        return codeValidationRepository.findByCode(code);
    }
}
