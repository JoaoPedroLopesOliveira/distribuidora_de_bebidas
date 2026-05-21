package com.distribuidora.usuarios.service;

import com.distribuidora.usuarios.model.Usuario;
import com.distribuidora.usuarios.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public Usuario criar(Usuario usuario) {
        return usuariosRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuario> buscarIdUser(Long id) {
        return usuariosRepository.findById(id);
    }

    public Optional<Usuario> buscarUser(String user) {
        return usuariosRepository.findByUser(user);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuariosRepository.findById(id).map(usuario ->{
            usuario.setUser(usuarioAtualizado.getUser());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setRole(usuarioAtualizado.getRole());
            return usuariosRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletar (Long id) {
        usuariosRepository.deleteById(id);
    }

}
