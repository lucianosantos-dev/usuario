package com.lucianodev.usuario.business.mapper;

import com.lucianodev.usuario.business.dto.EnderecoDto;
import com.lucianodev.usuario.business.dto.TelefoneDto;
import com.lucianodev.usuario.business.dto.UsuarioDto;
import com.lucianodev.usuario.infrastructure.entity.Endereco;
import com.lucianodev.usuario.infrastructure.entity.Telefone;
import com.lucianodev.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario paraUsuario(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEndereco(dto.getEnderecos()))
                .telefones(paraListaTelefone(dto.getTelefones()))
                .build();
    }

    public UsuarioDto paraUsuarioDto(Usuario entity) {
        return UsuarioDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .enderecos(paraListaEnderecoDto(entity.getEnderecos()))
                .telefones(paraListaTelefoneDto(entity.getTelefones()))
                .build();
    }

    public Endereco paraEndereco(EnderecoDto dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    public EnderecoDto paraEnderecoDto(Endereco entities) {
        return EnderecoDto.builder()
                .rua(entities.getRua())
                .numero(entities.getNumero())
                .complemento(entities.getComplemento())
                .cidade(entities.getCidade())
                .estado(entities.getEstado())
                .cep(entities.getCep())
                .build();
    }

    public Telefone paraTelefone(TelefoneDto dto) {
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }

    public TelefoneDto paraTelefoneDto(Telefone entity) {
        return TelefoneDto.builder()
                .numero(entity.getNumero())
                .ddd(entity.getDdd())
                .build();
    }


    public List<Endereco> paraListaEndereco(List<EnderecoDto> dtos) {
        return dtos.stream().map(this::paraEndereco).toList();
    }

    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> entities) {
        return entities.stream().map(this::paraEnderecoDto).toList();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDto> dtos) {
        return dtos.stream().map(this::paraTelefone).toList();
    }

    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> entities) {
        return entities.stream().map(this::paraTelefoneDto).toList();
    }

}
