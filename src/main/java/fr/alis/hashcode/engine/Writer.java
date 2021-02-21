package fr.alis.hashcode.engine;

import fr.alis.hashcode.model.EvenMorePizzaOutput;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.util.List;

public interface Writer<O> {
    void write(O output, String path);
    @SneakyThrows
    default void writeLines(List<String> lines, String path) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            for (String line : lines) {
                fileWriter.write(line + "\n");
            }
        }
    }
}
