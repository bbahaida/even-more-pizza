package fr.alis.hashcode.utils;


import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.model.Pizza;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EvenMorePizzaProcessorTest {

    private EvenMorePizzaProcessor processor = new EvenMorePizzaProcessor();
    Pizza pizza1 = Pizza.builder()
            .total(3)
            .index(0)
            .ingredients(Lists.list("onion", "pepper", "olive"))
            .build();
    Pizza pizza2 = Pizza.builder()
            .total(3)
            .index(1)
            .ingredients(Lists.list("tomato", "mushroom", "basil"))
            .build();
    Pizza pizza3 = Pizza.builder()
            .total(3)
            .index(2)
            .ingredients(Lists.list("chicken", "mushroom", "pepper"))
            .build();
    Pizza pizza4 = Pizza.builder()
            .total(3)
            .index(3)
            .ingredients(Lists.list("tomato", "mushroom", "basil"))
            .build();
    Pizza pizza5 = Pizza.builder()
            .total(2)
            .index(4)
            .ingredients(Lists.list("chicken", "basil"))
            .build();
    List<Pizza> pizzas = Lists.list(
            pizza1,
            pizza2,
            pizza3,
            pizza4,
            pizza5);
    EvenMorePizzaInput input = EvenMorePizzaInput.builder()
            .availablePizza(5)
            .teamsOfTwo(1)
            .teamsOfThree(2)
            .teamsOfFour(1)
            .pizzas(pizzas)
            .build();

    GAParams params = GAParams.builder()
            .maxGeneration(1000)
            .mutationRate(0.5)
            .tournamentSize(10)
            .build();
    @Test
    public void write_ShouldReturnOutputContains2Orders() {

        EvenMorePizzaOutput output = processor.process(input, params);
        assertThat(output.getScore()).isGreaterThan(70);
    }

}
