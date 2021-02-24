package fr.alis.hashcode;

import fr.alis.hashcode.engine.EvolutionStrategy;
import fr.alis.hashcode.engine.HashCodeEngine;
import fr.alis.hashcode.engine.Population;
import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.model.Pizza;
import fr.alis.hashcode.model.PizzaPopulation;
import fr.alis.hashcode.utils.EvenMorePizzaReader;
import fr.alis.hashcode.utils.EvenMorePizzaWriter;
import fr.alis.hashcode.engine.GAParams;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Application extends HashCodeEngine<Pizza, EvenMorePizzaInput, EvenMorePizzaOutput> {
    private final EvenMorePizzaReader reader;
    private final EvenMorePizzaWriter writer;

    public Application() {
        reader = new EvenMorePizzaReader();
        writer = new EvenMorePizzaWriter();
    }

    public static void main(String[] args) {
        Application app = new Application();
        List<String> files = Arrays.asList("a_example",
                "b_little_bit_of_everything",
                "c_many_ingredients",
                "d_many_pizzas",
                "e_many_teams");

        GAParams paramsA = GAParams.builder()
                .maxGeneration(5)
                .mutationRate(0.35)
                .tournamentSize(10)
                .populationSize(5)
                .filename(files.get(0))
                .build();
        GAParams paramsB = GAParams.builder()
                .maxGeneration(5)
                .mutationRate(0.45)
                .tournamentSize(10)
                .mutationTotal(20)
                .populationSize(5)
                .deltaConstant(100)
                .deltaVariance(0.001)
                .deltaStepper(3)
                .filename(files.get(1))
                .build();
        GAParams paramsC = GAParams.builder()
                .maxGeneration(5)
                .mutationRate(0.25)
                .tournamentSize(10)
                .populationSize(5)
                .filename(files.get(2))
                .deltaConstant(1000)
                .deltaVariance(0.001)
                .deltaStepper(3)
                .mutationTotal(1)
                .build();
        GAParams paramsD = GAParams.builder()
                .maxGeneration(5)
                .mutationRate(0.3)
                .tournamentSize(10)
                .populationSize(5)
                .mutationTotal(1)
                .deltaConstant(100)
                .deltaVariance(0.001)
                .deltaStepper(3)
                .filename(files.get(3))
                .build();
        GAParams paramsE = GAParams.builder()
                .maxGeneration(5)
                .mutationRate(0.3)
                .tournamentSize(10)
                .populationSize(5)
                .mutationTotal(1)
                .deltaConstant(100)
                .deltaVariance(0.001)
                .deltaStepper(3)
                .filename(files.get(4))
                .build();
        List<GAParams> gaParams = Arrays.asList(paramsA, paramsB, paramsC, paramsD, paramsE);

        Instant start = Instant.now();
        long score = gaParams.parallelStream().map(params -> app.solve(params, app.reader, app.writer, EvolutionStrategy.MUTATION))
                .peek(output -> System.out.printf("******* file: %s, best score: %d, generation: %d *******%n",
                        output.getParams().getFilename(), output.getScore(), output.getGeneration()))
                .mapToLong(EvenMorePizzaOutput::getScore)
                .sum();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.printf("solving the %d files took %d s%n", files.size(), timeElapsed / 1000);
        System.out.println("---------------------------------");
        System.out.printf("the total score: %d %n", score);
        System.out.println("---------------------------------");

    }


    @Override
    public Population<Pizza, EvenMorePizzaOutput> getInstance(int size, EvenMorePizzaInput input) {
        return new PizzaPopulation(size, input);
    }
}
