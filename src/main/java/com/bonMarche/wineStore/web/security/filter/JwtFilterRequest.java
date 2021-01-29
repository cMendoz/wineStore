package com.bonMarche.wineStore.web.security.filter;

import com.bonMarche.wineStore.domain.service.BonMarcheUserDetailsService;
import com.bonMarche.wineStore.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//este método extiende o hereda del método once... de SPring Security, que hace que el filtro se ejecute cada que haya una peticion
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private BonMarcheUserDetailsService bonMarcheUserDetailsService;

    //implemento un método de la clase desde la cual extiendo o heredo. Dentro de este debo hacer es verificar si lo que viene en el encabezado
    //de la petición es un toquen, y si el mismo es correcto
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //capturo el encabezado de la petición :
        String authorizationHeader = request.getHeader("Authorization");
        // Si es distinto a NULL y si el authorizationHeader empieza con "Bearer" (cuando usamos JWT debemos de utilizar el prefijo Bearer)
        //Si se cumplen esas dos condiciones es porque ahí viene un JWT:
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            //Capturamos el JWT desde la posición 7 descontando Bearer y el espacio
            String jwt  = authorizationHeader.substring(7);
            //Verifico el usuario de ese JWT, primero arriba inyecto el JWTUtil, y uso el metodo que cree ahi extractUserName para verificar.
            String username = jwtUtil.extractUserName(jwt);
            //si el username no es nulo, y no se haya logeado ya, (verificamos que el usuario no haya ingresado a la api)
            //Eso se usa para verificar en el contexto que aun no exista una autenticación para este usuario
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //obtenemos los userDetails, para eso insertamos arriba el servicio bonMarcheUserDetailsService que hace de autenticación (ahí tengo los credenciales)
                //Este servicio nos verifica ese usuario, con el que llegó en el usuario del JWT, verifica si existe dentro de mi sistema de autenticación
                UserDetails userDetails = bonMarcheUserDetailsService.loadUserByUsername(username);
                //preguntamos si el JWT es correcto, le mandamos el token y userDetails
                if (jwtUtil.validateToken(jwt, userDetails)){
                    //Si lo es, levantamos esa sesión para ese usuario, le mandamos userDetails, null porque no tiene credenciales particulares,
                    //y userDetails.getAuthorities() que es p que se envíen ahi todos los roles que tiene el usuario, en este caso nada porque no le asiganamos
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //Le asignamos a Authtoken los detalles de la conexión que está recibiendo, y le mandamos el request con el fin de que podamos evaluar
                    //qué navegador estaba usando, sist op, horario de conexión, etc.
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //Aquí le asignamos la autenticación, para que la proxima vez no tenga que pasar por toda la validación de este filtro,
                    //gracias a esta validadción
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        }
        //Para finalizar le indicamos que el filtro sea evaluado con filterchain. Sólo queda editar el archivo securityConfig.java indicar
        //que este filtro será el encargado de recibir todas las peticiones y procesarlas.
        filterChain.doFilter(request, response);
    }
}
