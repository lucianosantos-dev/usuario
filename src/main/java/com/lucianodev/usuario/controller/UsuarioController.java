package com.lucianodev.usuario.controller;

import com.lucianodev.usuario.business.UsuarioService;
import com.lucianodev.usuario.business.dto.EnderecoDto;
import com.lucianodev.usuario.business.dto.TelefoneDto;
import com.lucianodev.usuario.business.dto.UsuarioDto;
import com.lucianodev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDto> save(@RequestBody UsuarioDto dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(service.save(dto));
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> update(@RequestBody UsuarioDto dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.update(token, dto));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDto usuarioDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(),
                        usuarioDto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDto> searchUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(service.searchUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        service.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDto> atualizaEndereco(@RequestBody EnderecoDto dto, @RequestParam("id") Long id) {
        return ResponseEntity.ok(service.atualizaEndereco(id, dto));
    }

    @PutMapping("telefone")
    public ResponseEntity<TelefoneDto> atualizaTelefone(@RequestBody TelefoneDto dto, @RequestParam("id") Long id) {
        return ResponseEntity.ok(service.atualizaTelefone(id, dto));
    }
}
