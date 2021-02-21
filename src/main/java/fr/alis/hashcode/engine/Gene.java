package fr.alis.hashcode.engine;

public interface Gene<T> {
    int getReference();
    void setReference(int reference);

    void swap(Gene<T> gene);

    T getARN();

    Gene<T> copy();
}
