package fr.alis.hashcode.model;

import fr.alis.hashcode.engine.GAParams;
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
    private long score;
    private int generation;
    private GAParams params;
    private List<PizzaOrder> orders;
}
