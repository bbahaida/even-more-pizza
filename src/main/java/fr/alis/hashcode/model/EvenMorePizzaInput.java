package fr.alis.hashcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvenMorePizzaInput {
    private int availablePizza;
    private int teamsOfTwo;
    private int teamsOfThree;
    private int teamsOfFour;
    private List<Pizza> pizzas;

    public EvenMorePizzaInput(EvenMorePizzaInput input) {
        availablePizza = input.getAvailablePizza();
        teamsOfTwo = input.getTeamsOfTwo();
        teamsOfThree = input.getTeamsOfThree();
        teamsOfFour = input.getTeamsOfFour();
        pizzas = input.getPizzas().stream().collect(Collectors.toList());
    }

    public EvenMorePizzaInput copy() {
        return new EvenMorePizzaInput(this);
    }
}
