package com.gerenciamento.financeiro_pessoal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gerenciamento.financeiro_pessoal.Model.Usuario;
import com.gerenciamento.financeiro_pessoal.Service.UsuarioService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastraUsuario")
    public ResponseEntity<?> cadastroDeUsuario(@RequestBody Usuario usuario){
        Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }


    @PutMapping("atualizaUsuario")
    public ResponseEntity<?> atualizaUsuario(@RequestParam Integer usuarioId, @RequestBody Usuario usuario ) {
        Usuario atualizaUsuario = usuarioService.updateUsuario(usuarioId, usuario);
        return ResponseEntity.ok(atualizaUsuario);
    }
    
    
}
