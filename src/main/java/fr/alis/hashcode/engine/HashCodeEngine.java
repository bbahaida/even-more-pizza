package fr.alis.hashcode.engine;

public abstract class HashCodeEngine<T, I, O> {

    public O process(I input, GAParams params) {
        PossibleSolution<T, O> fittestSolution = null;
        int generation = 0;
        while (!terminationCondition(generation, params.getMaxGeneration())) {
            Population<T, O> population = evolvePopulation(input, params);
            ++generation;

            PossibleSolution<T, O> solution = population.getFittestSolution();
            if (fittestSolution == null ||
                    solution.getScore() > fittestSolution.getScore()) {
                fittestSolution = solution.copy();
            }

        }

        // find out the best distribution
        return fittestSolution.asOutput();
    }

    private boolean terminationCondition(int generation, int maxGeneration) {
        return generation >= maxGeneration;
    }

    private Population<T,O> evolvePopulation(I input, GAParams params) {
        Population<T,O> newPopulation = getInstance(params.getPopulationSize(), input);
        newPopulation.initialize();

        for (int i = 0; i < params.getPopulationSize(); i++) {
            newPopulation.saveSolution(i, mutate(newPopulation.getSolution(i), params.getMutationRate()));
        }

        return newPopulation;
    }

    private PossibleSolution<T, O> mutate(PossibleSolution<T, O> solution,
                                                                double mutationRate) {
        for (int i = 0; i < solution.getSize(); i++) {
            if (Math.random() <= mutationRate) {
                solution.swap(solution.getGene(), solution.getGene());
            }
        }
        return solution;
    }


    public abstract Population<T, O> getInstance(int size, I input);

}
