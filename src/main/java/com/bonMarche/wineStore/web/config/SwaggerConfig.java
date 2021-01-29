package com.bonMarche.wineStore.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //anotación de Spring
@EnableSwagger2
public class SwaggerConfig {

    //agregamos un Bean:
    @Bean
    public Docket api(){ //retorna un docket
        return new Docket(DocumentationType.SWAGGER_2) //le indicamos el tipo de documentacion q utilizaremos
                .select()//le indicamos qué queremos que exporte o exponga en la documentación con el metodo select().apis()
                .apis(RequestHandlerSelectors.basePackage("com.bonMarche.wineStore.web.controller")) //dentro de apis le indicamos que sólo queremos que sean expuestos a traves de la documentacion los que están dentro del paquete controller. Porque aquí tenemos nuestros end points de la API.
                .build(); //finalizamos con build, para que construya esta respuesta
    }
}
