package br.inatel.manager.controllers;

import br.inatel.manager.entities.NotificationBase;
import br.inatel.manager.entities.Stock;
import br.inatel.manager.entities.StockBase;
import br.inatel.manager.exceptions.StockNotFoundException;
import br.inatel.manager.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping()
    public List<Stock> allStocks() {
        return stockService.listAll();
    }

    @GetMapping("/{id}")
    public Stock getById(@PathVariable("id") String id) {
        final Optional<Stock> stock = stockService.findById(id);
        if (!stock.isPresent()) {
            throw new StockNotFoundException();
        }
        return stock.get();
    }

    @PostMapping()
    public Stock saveStock(@RequestBody Stock stock) {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<StockBase> mono = webClient.get()
                .uri("/stock/" + stock.getId())
                .retrieve()
                .bodyToMono(StockBase.class);
        StockBase base = mono.block();
        if (base == null) {
            NotificationBase notification = new NotificationBase();
            notification.setHost("localhost");
            notification.setPort("8081");
            webClient.post()
                    .uri("/notification")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(notification), NotificationBase.class)
                    .retrieve()
                    .bodyToMono(NotificationBase.class);
            base = new StockBase();
            base.setId(stock.getId());
            base.setDescription("");
            webClient.post()
                    .uri("/stock")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(base), StockBase.class)
                    .retrieve()
                    .bodyToMono(StockBase.class);
        }
        return this.stockService.saveStock(stock);
    }

}
