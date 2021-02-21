package fr.alis.hashcode.model;

import fr.alis.hashcode.engine.Gene;
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
public class Team implements Gene<Pizza> {
    private int members;
    @Builder.Default
    private List<Pizza> pizzas = new ArrayList<>();

    private int reference;

    public Team(Team team) {
        members = team.members;
        pizzas = new ArrayList<>(team.pizzas);
        reference = team.reference;
    }

    public long getScore() {
        long ingredients = pizzas.stream().flatMap(pizza -> pizza.getIngredients().stream()).distinct().count();
        return (long) Math.pow(ingredients, 2);
    }

    @Override
    public void swap(Gene<Pizza> gene) {
        pizzas.remove(reference);
        pizzas.add(reference, gene.getARN());
    }

    @Override
    public Pizza getARN() {
        return pizzas.get(reference);
    }

    @Override
    public Gene<Pizza> copy() {
        return new Team(this);
    }
}
