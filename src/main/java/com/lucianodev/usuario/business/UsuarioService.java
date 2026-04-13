package com.lucianodev.usuario.business;

import com.lucianodev.usuario.business.dto.UsuarioDto;
import com.lucianodev.usuario.business.mapper.UsuarioMapper;
import com.lucianodev.usuario.infrastructure.entity.Usuario;
import com.lucianodev.usuario.infrastructure.exceptions.ConflitException;
import com.lucianodev.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucianodev.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder;


    public UsuarioDto save(UsuarioDto dto) {
        existEmail(dto.getEmail());
        dto.setSenha(encoder.encode(dto.getSenha()));
        Usuario usuario = mapper.paraUsuario(dto);
        return mapper.paraUsuarioDto(repository.save(usuario));
    }

    public void existEmail(String email) {
        try {
            boolean exists = hasEmail(email);
            if (exists) {
                throw new ConflitException("Email já cadastrado" + email);
            }
        } catch (ConflitException e) {
            throw new ConflitException("Email já cadastrado", e.getCause());
        }
    }

    public boolean hasEmail(String email) {
        return repository.existsByEmail(email);
    }

    public Usuario searchUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado!"));
    }

    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}
