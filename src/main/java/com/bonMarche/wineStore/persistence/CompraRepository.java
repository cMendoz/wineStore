package com.bonMarche.wineStore.persistence;

import com.bonMarche.wineStore.domain.Purchase;
import com.bonMarche.wineStore.domain.repository.PurchaseRepository;
import com.bonMarche.wineStore.persistence.crud.CompraCrudRepository;
import com.bonMarche.wineStore.persistence.entity.Compra;
import com.bonMarche.wineStore.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //para indicar que es un repositorio spring y que se va a comunicar con la base de datos.
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll()); //porque compraCrudRepository.findAll() retorna una lista de COMPRAS, y necesitamos una lista de PURCHASES, por eso llamamos al mapper. List<Compra> es para castear
    }

    @Override
    public Optional<List<Purchase>> getByCliente(String clientId) {
        //Para hacer esto (no tenemos una opción automática como findAll, hay que hacer un query method en CompraCrudRepository)
        return compraCrudRepository.findByIdCliente(clientId).map(compras -> mapper.toPurchases(compras)); //hago la funcion la,bda para convertir compras a purchases si recibo algo del query al que llamo.
    }

    @Override
    public Purchase save(Purchase purchase) {
        //Primero convertimos purchase a compra, antes de guardar
        Compra compra = mapper.toCompra(purchase);
        //Garantizarnos que se guardará en cascada:
        compra.getVinos().forEach(vino -> vino.setCompra(compra)); //compra ya conoce sus vinos, acá le decimos a los vinos a qué compra pertenecen
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
