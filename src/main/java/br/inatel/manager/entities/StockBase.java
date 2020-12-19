package br.inatel.manager.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
public class StockBase {
    private String id;
    private String description;
}
