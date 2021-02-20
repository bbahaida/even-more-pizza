package fr.alis.hashcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private int members;
    @Builder.Default
    private List<Pizza> pizzas = new ArrayList<>();

    public long getScore() {
        long ingredients = pizzas.stream().flatMap(pizza -> pizza.getIngredients().stream()).distinct().count();
        return (long) Math.pow(ingredients, 2);
    }
}
