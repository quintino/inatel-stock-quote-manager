package br.inatel.manager.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "stock")
public class Stock {
    @Id
    @Column(length = 50)
    private String id;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="stock.id", nullable=false)
    private Set<Quote> quotes;
}
