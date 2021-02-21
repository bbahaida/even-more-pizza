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

        GAParams paramsA = GAParams.builder()
                .maxGeneration(5)
                .mutationRate(0.35)
                .tournamentSize(10)
                .populationSize(100)
                .filename(files.get(0))
                .build();
        GAParams paramsB = GAParams.builder()
                .maxGeneration(10)
                .mutationRate(0.25)
                .tournamentSize(10)
                .populationSize(200)
                .filename(files.get(1))
                .build();
        GAParams paramsC = GAParams.builder()
                .maxGeneration(10)
                .mutationRate(0.55)
                .tournamentSize(10)
                .populationSize(400)
                .filename(files.get(2))
                .build();
        GAParams paramsD = GAParams.builder()
                .maxGeneration(7)
                .mutationRate(0.35)
                .tournamentSize(10)
                .populationSize(20)
                .filename(files.get(3))
                .build();
        GAParams paramsE = GAParams.builder()
                .maxGeneration(7)
                .mutationRate(0.35)
                .tournamentSize(10)
                .populationSize(20)
                .filename(files.get(4))
                .build();
        List<GAParams> gaParams = Arrays.asList(paramsA, paramsB, paramsC, paramsD, paramsE);

        Instant start = Instant.now();
        gaParams.parallelStream().forEach(app::solve);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.printf("solving the %d files took %d s%n", files.size(), timeElapsed / 1000);
        System.out.println("---------------------------------");
        System.out.printf("the total score: %d %n", score);
        System.out.println("---------------------------------");

    }

    public void solve(GAParams params) {
        System.out.printf("engine params: %s %n", params.toString());
        System.out.printf("reading file **** %s **** started%n", params.getFilename());
        String prefix = "src/main/resources/";
        String inputFilePath = prefix + params.getFilename() + ".in";
        // read the file
        EvenMorePizzaInput input = reader.read(inputFilePath);

        System.out.printf("reading file **** %s **** done%n", params.getFilename());

        // process the data
        System.out.printf("Start Processing %s %n", params.getFilename());
        EvenMorePizzaOutput output = engine.process(input, params);

        System.out.printf("%s selected score: %d %n", params.getFilename(), output.getScore());

        score += output.getScore();
        // write the data
        System.out.printf("writing file **** %s **** started%n", params.getFilename());
        String outputFilePath = prefix + params.getFilename() + ".out";
        writer.write(output, outputFilePath);
        System.out.printf("writing file **** %s **** done%n", params.getFilename());
    }
}
