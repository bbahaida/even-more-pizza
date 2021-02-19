package fr.alis.hashcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvenMorePizzaInput {
    private int availablePizza;
    private int teamsOfTwo;
    private int teamsOfThree;
    private int teamsOfFour;
    private List<PizzaIngredient> ingredients;
}
