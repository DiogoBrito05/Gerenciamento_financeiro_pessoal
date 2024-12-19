package com.gerenciamento.financeiro_pessoal.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuthDTO {

    private String access;

    private String refresh;

    public String getAccess() {
        return access;
    }

    public  AuthDTO(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }
}
