package com.lucianodev.usuario.business;

import com.lucianodev.usuario.business.dto.UsuarioDto;
import com.lucianodev.usuario.business.mapper.UsuarioMapper;
import com.lucianodev.usuario.infrastructure.entity.Usuario;
import com.lucianodev.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;


    public UsuarioDto save(UsuarioDto dto) {
        Usuario usuario = mapper.paraUsuario(dto);
        return mapper.paraUsuarioDto(repository.save(usuario));
    }
}
