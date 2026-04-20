package com.lucianodev.usuario.business;

import com.lucianodev.usuario.business.dto.EnderecoDto;
import com.lucianodev.usuario.business.dto.TelefoneDto;
import com.lucianodev.usuario.business.dto.UsuarioDto;
import com.lucianodev.usuario.business.mapper.UsuarioMapper;
import com.lucianodev.usuario.infrastructure.entity.Endereco;
import com.lucianodev.usuario.infrastructure.entity.Telefone;
import com.lucianodev.usuario.infrastructure.entity.Usuario;
import com.lucianodev.usuario.infrastructure.exceptions.ConflitException;
import com.lucianodev.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucianodev.usuario.infrastructure.repository.EnderecoRepository;
import com.lucianodev.usuario.infrastructure.repository.TelefoneRepository;
import com.lucianodev.usuario.infrastructure.repository.UsuarioRepository;
import com.lucianodev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


    public UsuarioDto save(UsuarioDto dto) {
        existEmail(dto.getEmail());
        dto.setSenha(encoder.encode(dto.getSenha()));
        Usuario usuario = mapper.paraUsuario(dto);
        return mapper.paraUsuarioDto(repository.save(usuario));
    }

    public UsuarioDto update(String token, UsuarioDto usuarioDto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario entity = repository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));

        usuarioDto.setSenha(usuarioDto.getSenha() != null ? encoder.encode(usuarioDto.getSenha()) : null);
        Usuario usuario = mapper.updateUsuario(usuarioDto, entity);
        repository.save(usuario);
        return mapper.paraUsuarioDto(usuario);
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

    public UsuarioDto searchUserByEmail(String email) {
        try {
            return mapper.paraUsuarioDto(repository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado!")));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado");
        }

    }

    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }

    public EnderecoDto atualizaEndereco(Long id, EnderecoDto dto) {
        Endereco entity = enderecoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado!"));

        Endereco endereco = mapper.updateEndereco(dto, entity);
        enderecoRepository.save(endereco);
        return mapper.paraEnderecoDto(endereco);
    }

    public TelefoneDto atualizaTelefone(Long id, TelefoneDto dto) {
        Telefone entity = telefoneRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado!"));

        Telefone telefone = mapper.updateTelefone(dto, entity);
        telefoneRepository.save(telefone);
        return mapper.paraTelefoneDto(telefone);

    }
}
