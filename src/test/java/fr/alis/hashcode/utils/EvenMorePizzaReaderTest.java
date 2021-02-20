package fr.alis.hashcode.utils;

import fr.alis.hashcode.model.EvenMorePizzaInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EvenMorePizzaReaderTest {

    private EvenMorePizzaReader reader = new EvenMorePizzaReader();

    @Test
    public void read_ShouldReturn5AvailablePizzas(){
        EvenMorePizzaInput input = reader.read("src/test/resources/b_little_bit_of_everything.in");
        Assertions.assertThat(input.getAvailablePizza()).isEqualTo(500);
    }

}
