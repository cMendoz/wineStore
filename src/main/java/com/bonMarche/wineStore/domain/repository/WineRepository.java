package com.bonMarche.wineStore.domain.repository;

import com.bonMarche.wineStore.domain.Wine;

import java.util.List;
import java.util.Optional;

public interface WineRepository {
    List<Wine> getAll();
    Optional<List<Wine>> getByCategory(int categoryId);
    Optional<List<Wine>> getScarseWines(int quantity);
    Optional<Wine> getWine(int wineId);
    Wine save(Wine wine);
    void delete(int wineId);
}
