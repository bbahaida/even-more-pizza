package fr.alis.hashcode.utils;

import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;

public class EvenMorePizzaProcessor {
    public EvenMorePizzaOutput process(EvenMorePizzaInput input) {


        // find how many orders to deliver
        int total = getTotal(input);

        // find out the best distribution
        return new EvenMorePizzaOutput();
    }

    protected int getTotal(EvenMorePizzaInput input) {
        int pizzas = input.getAvailablePizza();
        int total2 = input.getTeamsOfTwo() * 2;
        int total3 = input.getTeamsOfTwo() * 3;
        int total4 = input.getTeamsOfTwo() * 4;


        return 0;
    }
}
