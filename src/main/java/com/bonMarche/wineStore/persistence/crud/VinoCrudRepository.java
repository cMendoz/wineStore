package com.bonMarche.wineStore.persistence.crud;

import com.bonMarche.wineStore.persistence.entity.Vino;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VinoCrudRepository extends CrudRepository<Vino, Integer> {

    //Query Nativo:
    @Query(value = "SELECT * FROM vinos WHERE id_categoria = ?", nativeQuery = true)

    //Query methods: nos ofrece mucha + flexibilidad
    List<Vino> findByIdCategoria (int idCategoria);

    //más ejemplos: FIND BY categoria ORDER BY nombre ASC
    List<Vino> findByIdCategoriaOrderByNombreAsc (int idCategoria);

    // FIND BY cantidadStock LESS THAN && estado
    Optional<List<Vino>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);
}
