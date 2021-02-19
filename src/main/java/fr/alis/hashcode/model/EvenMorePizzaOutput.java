package fr.alis.hashcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvenMorePizzaOutput {
    private int totalTeams;
    private List<PizzaOrder> orders;
}
