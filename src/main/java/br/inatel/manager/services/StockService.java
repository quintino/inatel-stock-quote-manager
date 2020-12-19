package br.inatel.manager.services;

import br.inatel.manager.entities.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {
    Optional<Stock> findById(String id);

    List<Stock> listAll();

    Stock saveStock(Stock stock);
}
