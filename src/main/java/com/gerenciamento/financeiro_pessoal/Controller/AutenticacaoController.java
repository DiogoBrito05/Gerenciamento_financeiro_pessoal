package com.gerenciamento.financeiro_pessoal.Controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.gerenciamento.financeiro_pessoal.Model.AuthLogin;
import com.gerenciamento.financeiro_pessoal.Service.AutenticacaoService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService service;
    
        @Deprecated
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody @Validated AuthLogin login, HttpServletResponse response) {
         var jwt = service.autenticacaoSistema(login, response);
                return ResponseEntity.ok(jwt);
        }
        
}
