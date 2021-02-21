package fr.alis.hashcode.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GAParams {
    @Builder.Default
    private int tournamentSize = 10;
    @Builder.Default
    private int maxGeneration = 100;
    @Builder.Default
    private int populationSize = 100;
    @Builder.Default
    private double crossoverRate = 0.5;
    @Builder.Default
    private double mutationRate = 0.15;
    private String filename;
}
