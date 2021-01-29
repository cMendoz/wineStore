package com.bonMarche.wineStore.persistence.mapper;

import com.bonMarche.wineStore.domain.Category;
import com.bonMarche.wineStore.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring") //para indicar que es un mapeador, y que es un componente de tipo spring
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active")
    })
    Category toCategory(Categoria categoria);

    //hace exactamente el mismo mapeo que el mappings superior, pero a la inversa por lo que no es necesario mapear nuevamente cada campo
    @InheritInverseConfiguration
    @Mapping(target= "vinos", ignore = true) //productos es el campo que no se mapea en la version ingles (category), le indicamos que debe ignorarlo
    Categoria toCategoria(Category category);

}
