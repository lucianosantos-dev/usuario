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

    public EnderecoDto paraEnderecoDto(Endereco entity) {
        return EnderecoDto.builder()
                .id(entity.getId())
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
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
                .id(entity.getId())
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

    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario entity) {
        return Usuario.builder()
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : entity.getSenha())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDto dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDto dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDto dto, Long id){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuarioId(id)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDto dto, Long id){
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuarioId(id)
                .build();
    }

}
