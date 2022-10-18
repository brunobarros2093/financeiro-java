package com.moneyawapi.security;

import com.moneyawapi.model.Usuario;
import com.moneyawapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha incorreto"));
        return new User(email, usuario.getSenha(), getPermissao(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissao(Usuario usuario) {
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        usuario.getPermissoes().forEach(p -> authoritySet.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
        return authoritySet;
    }
}
