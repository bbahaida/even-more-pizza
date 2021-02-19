package fr.alis.hashcode.utils;


import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.model.PizzaIngredient;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EvenMorePizzaProcessorTest {

    private EvenMorePizzaProcessor processor = new EvenMorePizzaProcessor();

    PizzaIngredient pizzaIngredient1 = PizzaIngredient.builder()
            .total(3)
            .ingredients(Lists.list("onion", "pepper", "olive"))
            .build();
    PizzaIngredient pizzaIngredient2 = PizzaIngredient.builder()
            .total(3)
            .ingredients(Lists.list("tomato", "mushroom", "basil"))
            .build();
    PizzaIngredient pizzaIngredient3 = PizzaIngredient.builder()
            .total(3)
            .ingredients(Lists.list("chicken", "mushroom", "pepper"))
            .build();
    PizzaIngredient pizzaIngredient4 = PizzaIngredient.builder()
            .total(3)
            .ingredients(Lists.list("tomato", "mushroom", "basil"))
            .build();
    PizzaIngredient pizzaIngredient5 = PizzaIngredient.builder()
            .total(2)
            .ingredients(Lists.list("chicken", "basil"))
            .build();
    List<PizzaIngredient> ingredients = Lists.list(
            pizzaIngredient1,
            pizzaIngredient2,
            pizzaIngredient3,
            pizzaIngredient4,
            pizzaIngredient5);
    EvenMorePizzaInput input = EvenMorePizzaInput.builder()
            .availablePizza(5)
            .teamsOfTwo(1)
            .teamsOfThree(2)
            .teamsOfFour(1)
            .ingredients(ingredients)
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
