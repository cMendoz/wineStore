package com.bonMarche.wineStore.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service //importante!! para despues poderlo inyectar.
public class BonMarcheUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("mariana", "{noop}mariana", new ArrayList<>()); //dentro de ArrayList van los roles
        //Este user es sólo un demo, lo que debería hacer es ir a una base de datos o contra un sistema como Auth0 para que verifique las credenciales de inicio de sesión antes de usar nuestros servicios
    }
}
