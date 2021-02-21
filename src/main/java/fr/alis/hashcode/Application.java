package fr.alis.hashcode;

import fr.alis.hashcode.engine.EvenMorePizzaEngine;
import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.utils.EvenMorePizzaReader;
import fr.alis.hashcode.utils.EvenMorePizzaWriter;
import fr.alis.hashcode.utils.GAParams;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Application {
    private EvenMorePizzaReader reader;
    private EvenMorePizzaWriter writer;
    private EvenMorePizzaEngine engine;
    private static int score = 0;

    public Application() {
        reader = new EvenMorePizzaReader();
        writer = new EvenMorePizzaWriter();
        engine = new EvenMorePizzaEngine();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Application app = new Application();
        List<String> files = Arrays.asList("a_example",
                "b_little_bit_of_everything",
                "c_many_ingredients",
                "d_many_pizzas",
                "e_many_teams");
        GAParams params = GAParams.builder()
                .maxGeneration(10)
                .mutationRate(0.35)
                .tournamentSize(10)
                .populationSize(100)
                .build();
        System.out.printf("engine params: %s %n", params.toString());
        Instant start = Instant.now();
        files.parallelStream().forEach(file -> app.solve(file, params));
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.printf("solving the %d files took %d s%n", files.size(), timeElapsed / 1000);
        System.out.println("---------------------------------");
        System.out.printf("the total score: %d %n", score);
        System.out.println("---------------------------------");

    }

    public void solve(String filename, GAParams params) {

        System.out.printf("reading file **** %s **** started%n", filename);
        String prefix = "src/main/resources/";
        String inputFilePath = prefix + filename + ".in";
        // read the file
        EvenMorePizzaInput input = reader.read(inputFilePath);

        System.out.printf("reading file **** %s **** done%n", filename);

        // process the data
        System.out.printf("Start Processing %s %n", filename);
        EvenMorePizzaOutput output = engine.process(input, params);

        System.out.printf("%s selected score: %d %n", filename, output.getScore());

        score += output.getScore();
        // write the data
        System.out.printf("writing file **** %s **** started%n", filename);
        String outputFilePath = prefix + filename + ".out";
        writer.write(output, outputFilePath);
        System.out.printf("writing file **** %s **** done%n", filename);
    }
}
