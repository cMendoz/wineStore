package com.bonMarche.wineStore.persistence.mapper;

import com.bonMarche.wineStore.domain.PurchaseItem;
import com.bonMarche.wineStore.persistence.entity.ComprasVino;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {WineMapper.class}) // Uses para indicar que la interface internamente tbn utiliza el mapeo WineMapper.Class
public interface PurchaseItemMapper {
    @Mappings({
            @Mapping(source = "id.idVino", target = "wineId"), //Entro a través de id. porque dentro de compras vino hay una clave primaria compuesta, a la que accedo con . y voy al id que me interesa
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "total", target = "total"),
            @Mapping(source = "estado", target = "active")
    })
    PurchaseItem toPurchaseItem(ComprasVino vino);

    @InheritInverseConfiguration //para que reutilice de manera inversa el mapeo de arriba
    // Como hay que agregar todos los atributos para mapearlos, es necesario ignorarlos:
    @Mappings({
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "vino", ignore = true),
            @Mapping(target = "id.idCompra", ignore = true)
    })
    ComprasVino toComprasVino(PurchaseItem item);




}
