package com.bonMarche.wineStore.web.security;

import com.bonMarche.wineStore.domain.service.BonMarcheUserDetailsService;
import com.bonMarche.wineStore.web.security.filter.JwtFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//El paquete security es el encargado de gestionar la seuridad
@EnableWebSecurity //Le indicamos que esta es la clase encargada de nuestra seguridad
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BonMarcheUserDetailsService bonMarcheUserDetailsService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;

    //code > generate > overideMethods > configure(auth:AuthenticationManagerBuilder)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth); remplazamos esto por:
        auth.userDetailsService(bonMarcheUserDetailsService); //para que use el usuario y contraseña que cree en BonMarcheUser..
    }

    //Le indicamos que queremos autorizar todas las peticiones que se hagan a /Autenticate porque para invocar ese servicio
    // NO necesitan estar autenticados porque con ese servicio lo van a hacer

    //generate > overideMethods > configure(http:HttpSecurity)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); sustituimos esto: csrf deshabilita las peticiones cruzadas, auth..Request autoriza las peticiones,
        // y a antMatchers le paso lo que quiero permitir : todas las peticiones que terminen en /authenticade sin importar lo que haya antes(/**/)
        http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate").permitAll() //permitAll que permita TODOO
                .anyRequest().authenticated() //con esto digo: todas las demas peticiones SI necesitan autenticación
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Ésta línea la agregué despues, cuando creé el JWT filter,
                // y es para indicarle que ese filtro será el ancargado de recibir todas las peticiones y procesarlas. Le indicamos que la session que usamos
                // dentro de nuestra APP sera STATELESS sin sesión xq los JWT son los que van a controlar cada petición en particular, sin manejar una sesión como tal.

        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class); //Le indicamos cual será el filtro. (Hay que inyectarlo arriba en el encabezado tbn).
        // Y le indicamos el TIPO de filtro UsernamePasswordAuthenticationFilter (es de tipo usuario y contraseña) y Cest FINI

    }

    //hago nuevamente un overide porque desde AuthControler usamos el authenticationManager de Spring

    @Override
    @Bean //para que sepa que lo elegimos explicitamente (a spring) como gestor de la autenticación
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //si lo dejo tal cual es para que Spring siga controlando la autenticación, pero le añadimos @BEan
    }

}
