package com.bonMarche.wineStore.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component //Es importante que contenga alguna anotación spring para que pueda ser insertado
public class JWTUtil {

    private static final String KEY = "m4r14n4";
    public String generateToken(UserDetails userDetails){
        //utilizamos la clase Jwts con un builder que nos permite a traves de una secuencia de métodos construir nuestro JWT
        //primero le incluimos el usuario dentro de Subject, y le indicamos en que fecha fue creado el JWT (la actual)
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                //le damos una fecha de expiración de 10hrs:
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                //firmamos nuestro método con un algoritmo, para firmar ademas necesitamos una clave, que genero antes del presente método como una cte KEY
                .signWith(SignatureAlgorithm.HS256, KEY).compact(); //.compact() p finalizar, crear y retornar nuestro JWT a partir de los userDetails que tengamos
    }

    //Autorización con JWT

    //Creamos un nuevo método que nos sirve para validar si el token es correcto:
    public boolean validateToken(String token, UserDetails userDetails){
        return userDetails.getUsername().equals(extractUserName(token)) && !isTokenExpired(token);
        //verificamos que el usuario de la peticion es igual al del token, y que no haya expirado el token
    }

    //Hay que verificar 2 cosas: que el token corresponda a ese usuario, y que no haya vencido

    //creamos un método auxiliar que retorne los claims, que son los objetos dentro de json webToken
    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody(); //setSiggning le incluimos la llave de la firmaque hice mas arriba (m4r14n4),
        //una vez que verifica que la firma es correcta, le pido los claims o el cuerpo de mi JWT separado x cada uno de los objetos o claims
    }

    //método que se encarga de extraer el nombre del usuario del token y usamos el metodo que acabo de construir getClaims
    public String extractUserName(String token){
        return getClaims(token).getSubject(); //En el subject es donde está el nombre del usuario de la petición
    }

    //método que retorna un valor booleano, verifica si el token ya expiró tbn usa el método getclaim que creé
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date()); //obtenemos la fecha de vencimiento del token y preguntamos si esa es anterior a la actual
        //si esta antes de la fecha actual retorna TRUE (ya expiró), si no retorna FALSE (es válido aún )
    }
}
