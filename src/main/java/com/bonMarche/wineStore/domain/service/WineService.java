package com.bonMarche.wineStore.domain.service;

import com.bonMarche.wineStore.domain.Wine;
import com.bonMarche.wineStore.domain.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // también la podemmos reemplazar por @Component, pero service agrega una diferenciación semántica de qué es esta clase
public class WineService {
    @Autowired
    private WineRepository wineRepository;

    public List<Wine> getAll(){
        return wineRepository.getAll();
    }
    public Optional<Wine> getWine(int wineId){
        return wineRepository.getWine(wineId);
    }
    public Optional<List<Wine>> getByCategory(int categoryId){
        return wineRepository.getByCategory(categoryId);
    }
    public Wine save(Wine wine){
        return wineRepository.save(wine);
    }
    public boolean delete(int wineId){
        return getWine(wineId).map(wine -> {
            wineRepository.delete(wineId);
            return true;
        }).orElse(false);
    }
    /* otra manera de hacer el delete
    public boolean delete(int wineId){
        if (getWine(wineId).isPresent();){
            wineRepository.delete(wineId);
            return true;
        }else{
            return false;
        }
    }
     */

}
