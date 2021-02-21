package fr.alis.hashcode;

import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.model.Population;
import fr.alis.hashcode.utils.EvenMorePizzaProcessor;
import fr.alis.hashcode.utils.EvenMorePizzaReader;
import fr.alis.hashcode.utils.EvenMorePizzaWriter;
import fr.alis.hashcode.utils.GAParams;

import java.util.Arrays;
import java.util.List;

public class Application {
    private EvenMorePizzaReader reader;
    private EvenMorePizzaWriter writer;
    private EvenMorePizzaProcessor processor;
    public Application() {
        reader = new EvenMorePizzaReader();
        writer = new EvenMorePizzaWriter();
        processor = new EvenMorePizzaProcessor();
    }

    public static void main(String[] args) {
        Application app = new Application();
        List<String> files = Arrays.asList("a_example",
                "b_little_bit_of_everything",
                "c_many_ingredients",
                "d_many_pizzas",
                "e_many_teams");
        files.forEach(app::solve);

    }

    public void solve(String filename) {
        String prefix = "src/main/resources/";
        String inputFilePath = prefix + filename+".in";
        // read the file
        EvenMorePizzaInput input = reader.read(inputFilePath);


        // process the data
        GAParams params = GAParams.builder()
                .maxGeneration(7)
                .mutationRate(0.25)
                .tournamentSize(10)
                .populationSize(30)
                .build();

        Population population = new Population(30, input);
        population.initialize();
        EvenMorePizzaOutput output = processor.process(population, params);

        // write the data
        String outputFilePath = prefix + filename+".out";
        writer.write(output, outputFilePath);
    }
}
