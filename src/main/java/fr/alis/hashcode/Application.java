package fr.alis.hashcode;

import fr.alis.hashcode.model.EvenMorePizzaInput;
import fr.alis.hashcode.model.EvenMorePizzaOutput;
import fr.alis.hashcode.utils.EvenMorePizzaProcessor;
import fr.alis.hashcode.utils.EvenMorePizzaReader;
import fr.alis.hashcode.utils.EvenMorePizzaWriter;

public class Application {
    private EvenMorePizzaReader reader;
    private EvenMorePizzaWriter writer;
    private EvenMorePizzaProcessor processor;
    public Application() {
        reader = new EvenMorePizzaReader();
        writer = new EvenMorePizzaWriter();
        processor = new EvenMorePizzaProcessor();
    }

    public void solve() {
        String inputFilePath = "";
        // read the file
        EvenMorePizzaInput input = reader.read(inputFilePath);


        // process the data

        EvenMorePizzaOutput output = processor.process(input);

        // write the data
        String outputFilePath = "";
        writer.write(output, outputFilePath);
    }
}
