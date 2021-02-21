package fr.alis.hashcode.engine;

public interface PossibleSolution<T, O> {
    long getScore();

    int getSize();


    void swap(Gene<T> geneA, Gene<T> geneB);

    Gene<T> getGene();

    O asOutput();

    PossibleSolution<T, O> copy();
}
