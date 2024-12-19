package com.gerenciamento.financeiro_pessoal.Service;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gerenciamento.financeiro_pessoal.Config.Security.TokenProvider;
import com.gerenciamento.financeiro_pessoal.DTOs.AuthDTO;
import com.gerenciamento.financeiro_pessoal.Model.AuthLogin;
import com.gerenciamento.financeiro_pessoal.Model.Usuario;
import com.gerenciamento.financeiro_pessoal.Repository.UsuarioRepository;
import com.google.gson.Gson;


@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenProvider jwt;

    @Autowired
    private AuthenticationManager manager;


    final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    // Função de logger
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = null;
        Throwable result = throwable;
        while ((cause = result.getCause()) != null && result != cause) {
            result = cause;
        }
        return result;
    }

    
    @Override
    public UserDetails loadUserByUsername(String usuarioEmail) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(usuarioEmail);

    }

    @Deprecated
    public String autenticacaoSistema(AuthLogin login, HttpServletResponse response) {
        try {
           var authenticationtoken = new UsernamePasswordAuthenticationToken(login.email(),
                    login.senha());

            var authentication = manager.authenticate(authenticationtoken);

            var tokenJwt = jwt.geradorToken((Usuario) authentication.getPrincipal());

            var token = new AuthDTO(tokenJwt, null);

            Gson gson = new Gson();

            String json = gson.toJson(token);

            logger.info("Login feito com sucesso!");

            return json;
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            throw new BadCredentialsException("Usuário inexistente ou senha inválida");
        } 
    }
    

}
