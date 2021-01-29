package com.bonMarche.wineStore.web.controller;

import com.bonMarche.wineStore.domain.dto.AuthenticationRequest;
import com.bonMarche.wineStore.domain.dto.AuthenticationResponse;
import com.bonMarche.wineStore.domain.service.BonMarcheUserDetailsService;
import com.bonMarche.wineStore.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Este controlador hace  de autenticador
//Al ser un controlador recibe las mismas anotaciones que otros:
@RestController
@RequestMapping("/auth") //se le hacen las peticiones a traves de auth
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; //propio de spring, verifica si los datos del usuario son correctos

    @Autowired
    private BonMarcheUserDetailsService bonMarcheUserDetailsService; //Es el que se esta encargando de manejar la seguridad de usuario y contraseña

    @Autowired
    private JWTUtil jwtUtil; //Es donde manejamos la creación del JWT


    //Éste metodo responde un JWT cuando alguien intenta iniciar sesión. Para esto retornamos un ResponseEntity que devuelva el DTO AuthenticationResponse
    @PostMapping("/authenticate") //!! éste metodo recibirá peticiones a través de POST por esta ruta, ademas debemos agragarle la anotacion requestBody
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){ //El método recibe un autentication request DTO que ya creamos
        try { //para verificar que todoo se hace bien lo encapsulamos en un try catch
            //le pedimos al gestor de autenticaciones de Spring que verifique si el usuario y contraseña que le pasamos son correctos.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            //obtenemos los datos del usuario desde el servicio que creamos para esto. (importamos arriba BonMarche... )
            UserDetails userDetails = bonMarcheUserDetailsService.loadUserByUsername(request.getUsername());
            //genero finalmente el token al cual le mando por parámetro los userdetails
            String jwt = jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK); //acá encio el jwt con la respuesta OK
        }catch (BadCredentialsException e){//capturamos una excepción que ya tiene lista spring, ocurre cuando el usuario no se autentica (usuario o contraseña incorrectos)

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
