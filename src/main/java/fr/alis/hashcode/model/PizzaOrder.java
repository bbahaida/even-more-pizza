package fr.alis.hashcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaOrder {
    private int total;
    private List<Integer> pizzaIndexes;
}
