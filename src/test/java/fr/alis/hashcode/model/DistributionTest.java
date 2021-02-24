package fr.alis.hashcode.model;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DistributionTest {
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
    @Test
    public void initialize_ShouldNotBeEmpty(){
        Distribution p = new Distribution(input, true);
        long score = p.getScore();
        Assertions.assertThat(p.getTeams().size()).isNotZero();
        Assertions.assertThat(score).isNotZero();
    }
}
