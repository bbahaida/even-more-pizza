package fr.alis.hashcode.utils;

import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.model.PizzaOrder;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EvenMorePizzaWriterTest {
    private EvenMorePizzaWriter writer = new EvenMorePizzaWriter();

    @Test
    public void write_ShouldWrite3Lines(){
        PizzaOrder pizzaOrder1 = PizzaOrder.builder().total(2)
                .pizzaIndexes(Lists.list(1, 4))
                .build();
        PizzaOrder pizzaOrder2 = PizzaOrder.builder().total(3)
                .pizzaIndexes(Lists.list(0, 2, 3))
                .build();
        ArrayList<PizzaOrder> orders = Lists
                .list(pizzaOrder1, pizzaOrder2);
        EvenMorePizzaOutput output = EvenMorePizzaOutput.builder()
                .totalTeams(2)
                .orders(orders)
                .build();
        String path = "src/test/resources/test.out";
        writer.write(output, path);
        List<String> lines = EvenMorePizzaReader.getLines(path);
        Assertions.assertThat(lines.size()).isEqualTo(3);
    }
}
