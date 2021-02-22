package fr.alis.hashcode.model;

import fr.alis.hashcode.engine.Population;
import fr.alis.hashcode.engine.PossibleSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PizzaPopulation implements Population<Pizza, EvenMorePizzaOutput> {
    private final List<PossibleSolution<Pizza, EvenMorePizzaOutput>> possibleSolutions = new ArrayList<>();
    private final int size;
    private final EvenMorePizzaInput input;
    public PizzaPopulation(int size, EvenMorePizzaInput input) {
        this.size = size;
        this.input = input;
    }

    public void initialize() {
        for (int i = 0; i < size; i++) {
            possibleSolutions.add(new Distribution(input.copy()));
        }
    }

    public PossibleSolution<Pizza, EvenMorePizzaOutput> getFittestSolution(){
        return possibleSolutions.stream()
                .max(Comparator.comparing(PossibleSolution::getScore))
                .orElseThrow(IllegalArgumentException::new);
    }

    public int size() {
        return size;
    }

    public void saveSolution(int i, PossibleSolution<Pizza, EvenMorePizzaOutput> newIndividual) {
        if (possibleSolutions.size() > i) {
            possibleSolutions.remove(i);
        }
        possibleSolutions.add(i, newIndividual);
    }

    public PossibleSolution<Pizza, EvenMorePizzaOutput> getSolution(int i) {
        return possibleSolutions.get(i);
    }
}
