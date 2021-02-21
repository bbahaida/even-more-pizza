package fr.alis.hashcode.utils;

import fr.alis.hashcode.engine.Writer;
import fr.alis.hashcode.model.EvenMorePizzaOutput;

import java.util.ArrayList;
import java.util.List;

public class EvenMorePizzaWriter implements Writer<EvenMorePizzaOutput> {
    public void write(EvenMorePizzaOutput output, String path) {
        writeLines(convertToLines(output), path);
    }

    private List<String> convertToLines(EvenMorePizzaOutput output) {
        List<String> lines = new ArrayList<>();
        lines.add(output.getTotalTeams() + "");

        output.getOrders().forEach(pizzaOrder -> {
            StringBuilder line = new StringBuilder(pizzaOrder.getTotal() + "");
            pizzaOrder.getPizzaIndexes().forEach(index -> line.append(" ").append(index));
            lines.add(line.toString());
        });
        return lines;
    }
}
