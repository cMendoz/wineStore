package com.bonMarche.wineStore.persistence.crud;

import com.bonMarche.wineStore.persistence.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompraCrudRepository extends CrudRepository<Compra, Integer> {

    //Query method: FIND BY idCliente
    Optional<List<Compra>> findByIdCliente(String idCliente);
}
