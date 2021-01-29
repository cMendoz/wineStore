package com.bonMarche.wineStore.web.controller;

import com.bonMarche.wineStore.domain.Wine;
import com.bonMarche.wineStore.domain.service.WineService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wines")
public class WineController {
    //inyectar los servicios
    @Autowired //podemos usar la anotacion acá porque wine service tiene la anotación service que es de Spring
    private WineService wineService;

    //USANDO RESPONSE ENTITY:

    //este servicio se encarga de obtener toda la lista, de vinos que existen dentro de mi tienda
    @GetMapping("/all") //Esta anotación porque estamos obteniendo información y su path en el que funciona
    @ApiOperation("Get all wine store wines")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<Wine>> getAll(){
        return new ResponseEntity<>(wineService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{wineId}")//Entre corchetes pasamos el parámetro, ya que éste será cambiante, puedo ponerle el nombre que quiera, siemrpre irá a buscarlo como id
    @ApiOperation("Search a wine with an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Wine not found"),
    })
    public ResponseEntity<Wine> getWine(@ApiParam(value = "The id of the wine", required = true, example = "7") @PathVariable("wineId") int wineId){ //El path variable debe indicar el mismo atributo de getMapping
        return wineService.getWine(wineId)
                .map(wine-> new ResponseEntity<>(wine, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}") //le agregamos /category/ porque se parece mucho al getWine, y ambos parámetros son int, el sistema no sabría a cual se refiere, por eso hay que aclararlo
    public ResponseEntity<List<Wine>> getByCategory(@PathVariable("categoryId") int categoryId){
        return wineService.getByCategory(categoryId)
                .map(wines -> new ResponseEntity<>(wines, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Wine> save(@RequestBody Wine wine){ //@RequestBody, porque la información (el vino) no viaja en el path ahora va en el cuerpo.
        return new ResponseEntity<>(wineService.save(wine), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{wineId}")
    public ResponseEntity delete(@PathVariable("wineId") int wineId){
        if (wineService.delete(wineId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* ACá SIN USAR RESPONSEENTITY, Y MÁS ABAJO LA MANERA CORRECTA DE LO MISMO.
    //este servicio se encarga de obtener toda la lista, de vinos que existen dentro de mi tienda
    @GetMapping("/all") //Esta anotación porque estamos obteniendo información y su path en el que funciona
    public List<Wine> getAll(){
        return wineService.getAll();
    }

    @GetMapping("/{wineId}")//Entre corchetes pasamos el parámetro, ya que éste será cambiante, puedo ponerle el nombre que quiera, siemrpre irá a buscarlo como id
    public Optional<Wine> getWine(@PathVariable("wineId") int wineId){ //El path variable debe indicar el mismo atributo de getMapping
        return wineService.getWine(wineId);
    }

    @GetMapping("/category/{categoryId}") //le agregamos /category/ porque se parece mucho al getWine, y ambos parámetros son int, el sistema no sabría a cual se refiere, por eso hay que aclararlo
    public Optional<List<Wine>> getByCategory(@PathVariable("categoryId") int categoryId){
        return wineService.getByCategory(categoryId);
    }

    @PostMapping("/save")
    public Wine save(@RequestBody Wine wine){ //@RequestBody, porque la información (el vino) no viaja en el path ahora va en el cuerpo.
        return wineService.save(wine);
    }

    @DeleteMapping("/delete/{wineId}")
    public Boolean delete(@PathVariable("wineId") int wineId){
        return wineService.delete(wineId);
    }
     */

    //! En realidad NO debería poner delete ni save como parte de la url (ya que estos son parte del verbo del http) es una mala práctica.
    // Acá se hace para que se entienda, y sea más explícito el ejercicio.


    //Cómo debería SER:

    /*
    @GetMapping("/all")
    public List<Wine> getAll(){ return wineService.getAll(); }

    @GetMapping("/{id}")
    public Optional<Wine> getWine(@PathVariable("id") int wineId){ //El path variable debe indicar el mismo atributo de getMapping
        return wineService.getWine(wineId);
    }

    @GetMapping("/category/{id}")
    public Optional<List<Wine>> getByCategory(@PathVariable("id") int categoryId){
        return wineService.getByCategory(categoryId);
    }

    @PostMapping()
    public Wine save(@RequestBody Wine wine){
        return wineService.save(wine);
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable("id") int wineId){
        return wineService.delete(wineId);
    }
     */




}
