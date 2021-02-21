package fr.alis.hashcode.engine;

import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.model.Pizza;

public interface Population<T, I, O> {
    PossibleSolution<T, O> getFittestSolution();

    void saveSolution(int i, PossibleSolution<T, O> solution);

    PossibleSolution<T, O> getSolution(int index);

    void initialize();
}
