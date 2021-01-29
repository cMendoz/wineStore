package com.bonMarche.wineStore.persistence.mapper;

import com.bonMarche.wineStore.domain.Wine;
import com.bonMarche.wineStore.persistence.entity.Vino;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}) //agregamos uses, porque uno de los parámetros (category) pertenece a otra tabla ya mapeada. Es para que el sistema sepa con qué mapear categoria-category
public interface WineMapper {
    @Mappings({
            @Mapping(source = "idVino", target = "wineId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "precioVenta", target = "price"),
            @Mapping(source = "cantidadStock", target = "stock"),
            @Mapping(source = "estado", target = "active"),
            @Mapping(source = "categoria", target = "category"), //ojo verificar que todos los getters & setters en vino esten, incluso de los @...To... manytoone, onetomany, etc porque me estba fallando por eso el mapeo
    })
    Wine toWine(Vino vino); //singular
    List<Wine> toWines(List<Vino> vinos); //para convertir una lista List de wine que se llame toWines en plural, que reciba una lista de vino que se llame vinos.. !!
    // no hay que volver a definir el @mappings en List porque internamente mapStruct se va a encargar de que se comporte de la misma manera que la versión singular


    //hace exactamente el mismo mapeo que el mappings superior, pero a la inversa por lo que no es necesario mapear nuevamente cada campo
    @InheritInverseConfiguration
    @Mapping(target= "codigoBarras", ignore = true) //productos es el campo que no se mapea en la version ingles (category), le indicamos que debe ignorarlo
    Vino toVino(Wine wine);

}
