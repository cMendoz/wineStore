package com.bonMarche.wineStore.persistence;

import com.bonMarche.wineStore.domain.Wine;
import com.bonMarche.wineStore.domain.repository.WineRepository;
import com.bonMarche.wineStore.persistence.crud.VinoCrudRepository;
import com.bonMarche.wineStore.persistence.entity.Vino;
import com.bonMarche.wineStore.persistence.mapper.WineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository //porque es una clase que interactúa con la DB. También podría usar @Component que es una etiqueta gral indica que es un componente de spring. @Repository indica que tipo de componenete específico es,
public class VinoRepository implements WineRepository {
    @Autowired //le cedemos a spring el control para que cree las instancias de estos objetos.
    private VinoCrudRepository vinoCrudRepository;
    @Autowired
    private WineMapper mapper; //para hacer la conversión de vino a wine debo llamar al mapper

    @Override
    public List<Wine> getAll() {
        List<Vino> vinos = (List<Vino>) vinoCrudRepository.findAll();
        return mapper.toWines(vinos);
    }

    @Override
    public Optional<List<Wine>> getByCategory(int categoryId){
        List<Vino> vinos = vinoCrudRepository.findByIdCategoria(categoryId);
        return Optional.of(mapper.toWines(vinos));
    }

    @Override
    public Optional<List<Wine>> getScarseWines(int quantity) {
        Optional<List<Vino>> vinos = vinoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return vinos.map(prods -> mapper.toWines(prods));
    }

    @Override
    public Optional<Wine> getWine(int wineId) {
            return vinoCrudRepository.findById(wineId).map(vino -> mapper.toWine(vino));
    }

    @Override
    public Wine save(Wine wine) {
        Vino vino = mapper.toVino(wine);
        return mapper.toWine(vinoCrudRepository.save(vino));
    }

    @Override
    public void delete(int wineId){
        vinoCrudRepository.deleteById(wineId);
    }

    /*
    //query Methods:
    public List<Vino> getByCategoria(int idCategoria){
        return vinoCrudRepository.findByIdCategoria(idCategoria);
    }

    public List<Vino> getByCategoriaOrderAsc(int idCategoria){
        return vinoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategoria);
    }

    public Optional<List<Vino>> getVinosEscasos(int cantidad){
        return vinoCrudRepository.findByCantidadStockLessThanAndEstado(cantidad, true);

    }
    //me permite obtener un vino con su Id
    public Optional<Vino> getVino(int idVino){
        return vinoCrudRepository.findById(idVino);

    }
    //me permite guardar un vino
    public Vino save(Vino vino){
        return vinoCrudRepository.save(vino);
    }
    //me permite eliminar un vino
    public void delete(int idVino){
        vinoCrudRepository.deleteById(idVino);
    }

    */

}
