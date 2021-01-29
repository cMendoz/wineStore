package com.bonMarche.wineStore.persistence.mapper;

import com.bonMarche.wineStore.domain.Purchase;
import com.bonMarche.wineStore.domain.PurchaseItem;
import com.bonMarche.wineStore.persistence.entity.Compra;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PurchaseItemMapper.class})
public interface PurchaseMapper {

    @Mappings({
            @Mapping(source = "idCompra", target = "purchaseId"),
            @Mapping(source = "idCliente", target = "clientId"),
            @Mapping(source = "fecha", target = "date"),
            @Mapping(source = "medioPago", target = "paymentMethod"),
            @Mapping(source = "comentario", target = "comment"),
            @Mapping(source = "estado", target = "state"),
            @Mapping(source = "vinos", target = "items") //la s
    })
    Purchase toPurchase(Compra compra);
    List<Purchase> toPurchases(List<Compra> compras); //automáticamene adquiere el mapeo de la versión singular

    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true) //recordar que debemos tener todos los mapeos, y si no debemos al menos ignorarlos
    Compra toCompra(Purchase purchase);

}

