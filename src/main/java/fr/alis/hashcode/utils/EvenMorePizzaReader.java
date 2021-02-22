package fr.alis.hashcode.utils;

import fr.alis.hashcode.engine.Reader;
import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.Pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EvenMorePizzaReader extends Reader<EvenMorePizzaInput> {

    protected EvenMorePizzaInput parse(List<String> lines) {

        EvenMorePizzaInput input = new EvenMorePizzaInput();
        boolean firstLine = true;
        List<Pizza> ingredients = new ArrayList<>();
        int index = 0;
        for (String line : lines) {
            if (firstLine) {
                List<Integer> orders = Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                input.setAvailablePizza(orders.get(0));
                input.setTeamsOfTwo(orders.get(1));
                input.setTeamsOfThree(orders.get(2));
                input.setTeamsOfFour(orders.get(3));
                firstLine = false;
            } else {
                Pizza pizza = Pizza.builder()
                        .total(Integer.parseInt(line.substring(0, line.indexOf(' '))))
                        .index(index)
                        .ingredients(Arrays.asList(line.substring(line.indexOf(' ') + 1).split(" ").clone()))
                        .build();

                ingredients.add(pizza);
                index += 1;
            }
        }

        input.setPizzas(ingredients);
        return input;
    }
}
