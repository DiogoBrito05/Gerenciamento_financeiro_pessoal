package com.gerenciamento.financeiro_pessoal.Service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gerenciamento.financeiro_pessoal.Config.TrataErros.Execao;
import com.gerenciamento.financeiro_pessoal.Model.Usuario;
import com.gerenciamento.financeiro_pessoal.Repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

     @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //  @Autowired
    // private PasswordEncoder passwordEncoder;

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


    public Usuario cadastrarUsuario(Usuario usuario) {
        logger.info("Iniciando cadastro de Usuario com dados:  UsuarioID={}, Nome={}, Email={}, CPF={}, Senha={********}",
                usuario.getUsuarioId(), usuario.getNome(), usuario.getEmail(),  usuario.getCpf(),
                usuario.getUsuarioTrocaSenha());
        try {

            if (usuarioRepository.findByBuscaEmail(usuario.getEmail()).isPresent()) {
                logger.error("Usuário já cadastrado");
                throw new Execao.Conflict("Usuário já cadastrado");   
            }
          
            
            usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));;
            

            return usuarioRepository.save(usuario);
        }  catch (Execao.Conflict e) {
            throw  e;
        } catch (Exception e) {
            Throwable rootCause = getRootCause(e);
            logger.error("Erro ao cadastrar o usuário.", rootCause);
            throw new Execao.BadRequestException("Erro ao cadastrar o usuário. Detalhes: " + e.getMessage());
        }

    }


    public Usuario updateUsuario(Integer usarioId, Usuario usuario){
        try {

            Optional<Usuario> buscaUsuarioAntigo = usuarioRepository.findById(usuario.getUsuarioId());

            if (!buscaUsuarioAntigo.isPresent()) {
                throw new Execao.NotFoundException("Usuário não cadastrado");
            }
            
            Usuario existenteUsuario = buscaUsuarioAntigo.get();

            if(usuario.getNome() != null){
                existenteUsuario.setNome(usuario.getNome());
            }

            if (usuario.getEmail() !=  null) {
                existenteUsuario.setEmail(usuario.getEmail());
            }

            if (usuario.getCpf() !=  null) {
                existenteUsuario.setCpf(usuario.getCpf());
            }

            return usuarioRepository.save(existenteUsuario);
        }  catch (Execao.NotFoundException e) {
            throw  e;
        } catch (Exception e) {
            Throwable rootCause = getRootCause(e);
            logger.error("Erro ao atualizar o usuário.", rootCause);
            throw new Execao.BadRequestException("Erro ao atualizar o usuário. Detalhes: " + e.getMessage());
        }

    }


    
}
