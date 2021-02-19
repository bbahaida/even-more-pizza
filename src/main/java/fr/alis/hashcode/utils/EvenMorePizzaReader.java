package fr.alis.hashcode.utils;

import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.PizzaIngredient;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EvenMorePizzaReader {
    public EvenMorePizzaInput read(String path) {

        List<String> lines = getLines(path);

        return parse(lines);
    }

    private EvenMorePizzaInput parse(List<String> lines) {

        EvenMorePizzaInput input = new EvenMorePizzaInput();
        boolean firstLine = true;
        List<PizzaIngredient> ingredients = new ArrayList<>();
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
            }
            else {
                PizzaIngredient pizzaIngredient = PizzaIngredient.builder()
                        .total(Integer.parseInt(line.substring(0, line.indexOf(' '))))
                        .index(index)
                        .ingredients(Arrays.asList(line.substring(line.indexOf(' ') + 1).split(" ").clone()))
                        .build();

                ingredients.add(pizzaIngredient);
                index+=1;
            }
        }

        input.setIngredients(ingredients);
        return input;
    }

    protected static List<String> getLines(String filepath) {

        Path path = Paths.get(filepath);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;


        } catch (IOException ex) {
            throw new RuntimeException("Cannot read file " + path, ex);
        }
    }
}
