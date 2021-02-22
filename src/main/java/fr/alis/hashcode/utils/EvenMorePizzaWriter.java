package fr.alis.hashcode.utils;

import fr.alis.hashcode.engine.Writer;
import fr.alis.hashcode.model.EvenMorePizzaOutput;

import java.util.ArrayList;
import java.util.List;

public class EvenMorePizzaWriter extends Writer<EvenMorePizzaOutput> {

    protected List<String> convertToLines(EvenMorePizzaOutput output) {
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
