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
            .ingredients(Lists.list("onion", "pepper", "olive"))
            .build();
    Pizza pizza2 = Pizza.builder()
            .total(3)
            .ingredients(Lists.list("tomato", "mushroom", "basil"))
            .build();
    Pizza pizza3 = Pizza.builder()
            .total(3)
            .ingredients(Lists.list("chicken", "mushroom", "pepper"))
            .build();
    Pizza pizza4 = Pizza.builder()
            .total(3)
            .ingredients(Lists.list("tomato", "mushroom", "basil"))
            .build();
    Pizza pizza5 = Pizza.builder()
            .total(2)
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
    @Test
    public void write_ShouldReturnOutputContains2Orders() {

        EvenMorePizzaOutput output = processor.process(input);

        assertThat(output.getTotalTeams()).isEqualTo(2);
        assertThat(output.getOrders().size()).isEqualTo(2);
        assertThat(output.getOrders().get(0).getTotal()).isEqualTo(2);
        assertThat(output.getOrders().get(0).getPizzaIndexes().get(0)).isEqualTo(1);
        assertThat(output.getOrders().get(1).getPizzaIndexes().get(0)).isZero();
    }

    @Test
    public void getTotal_ShouldReturn2(){
        assertThat(processor.getTotal(input)).isEqualTo(2);
    }
}