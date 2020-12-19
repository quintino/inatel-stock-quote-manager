package br.inatel.manager.services;

import br.inatel.manager.entities.Stock;
import br.inatel.manager.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public Optional<Stock> findById(String id) {
        return this.stockRepository.findById(id);
    }

    @Override
    public List<Stock> listAll() {
        return this.stockRepository.findAll();
    }

    @Override
    public Stock saveStock(Stock stock) {
        return this.stockRepository.save(stock);
    }
}
